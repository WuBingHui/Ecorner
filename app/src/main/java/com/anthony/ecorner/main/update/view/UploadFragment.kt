package com.anthony.ecorner.main.update.view


import android.Manifest
import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.FileNotFoundException
import android.graphics.drawable.BitmapDrawable
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.main.update.view.viewModel.UploadViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import com.csnt.android_sport.main.base.BaseFragment
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.graphics.Bitmap
import com.anthony.ecorner.main.main.view.MainActivity
import java.io.ByteArrayOutputStream
import java.io.File
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.anthony.ecorner.R
import com.anthony.ecorner.main.home.view.HomeFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * A simple [Fragment] subclass.
 */
class UploadFragment : BaseFragment(), View.OnTouchListener, EasyPermissions.PermissionCallbacks {

    companion object {
        const val LOCATION_REQUEST_CODE = 1000
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(context, "您拒绝授權,並不再提醒", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this).setTitle("打开應用程序設置修改應用程式權限").build().show()
        } else {
            Toast.makeText(context, "您不允檔案存取", Toast.LENGTH_SHORT).show()
            checkPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        checkPermission()
    }

    private val viewModel by viewModel<UploadViewModel>()
    private val loadingDialog = CustomLoadingDialog.newInstance()
    private var selectType = ""
    private var fileList = ArrayList<File>()
    private var partList = ArrayList<MultipartBody.Part>()
    override fun getData() {


    }

    enum class SelectType(val value: String) {
        CHILD("child"),
        TRAVEL("travel"),
        HOSPITAL("hospital"),
        ELECTRIC("electric"),
        GAME("game")
    }

    sealed class ImageType(val value: Int) {
        object LEFT_IMAGE : ImageType(0)
        object MID_IMAGE : ImageType(1)
        object RIGHT_IMAGE : ImageType(2)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
        checkPermission()
    }

    private fun initView() {

        commodityDescriptionEditText.setOnTouchListener(this)

        leftImageView.setOnClickListener {
            setImage(0)
        }

        midImageView.setOnClickListener {
            setImage(1)
        }

        rightImageView.setOnClickListener {
            setImage(2)
        }


        childRadioButton.setOnClickListener {
            setRadioStatus(SelectType.CHILD.value)
        }
        travelRadioButton.setOnClickListener {
            setRadioStatus(SelectType.TRAVEL.value)
        }
        hospitalRadioButton.setOnClickListener {
            setRadioStatus(SelectType.HOSPITAL.value)
        }
        elerictRadioButton.setOnClickListener {
            setRadioStatus(SelectType.ELECTRIC.value)
        }
        gameRadioButton.setOnClickListener {
            setRadioStatus(SelectType.GAME.value)
        }

        uploadBtn.setOnClickListener {
            upLoad()
        }

    }

    private fun initViewModel() {
        viewModel.onUploaad.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "上傳成功", Toast.LENGTH_SHORT).show()
                    clearData()
                }
                Status.FAILED -> {
                    Toast.makeText(context, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })
    }

    private fun checkPermission() {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE) &&
            EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {

            Toast.makeText(context, "已獲取檔案存取權限", Toast.LENGTH_SHORT).show()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "應用需要訪問您的檔案,您需要在彈跳視窗允許使用",
                LOCATION_REQUEST_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun setImage(currentImageCode: Int) {

        if (!EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
            !EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            Toast.makeText(context, "無權限存取檔案，請至應用程式資訊允許檔案存取", Toast.LENGTH_SHORT).show()
            return
        }
//在Activity Action裡面有一個“ACTION_GET_CONTENT”字串常量，
// 該常量讓使用者選擇特定型別的資料，並返回該資料的URI.我們利用該常量，
//然後設定型別為“image/*”，就可獲得Android手機內的所有image。*/
        val intent = Intent()
        /* 開啟Pictures畫面Type設定為image */
        intent.type = "image/*"
        /* 使用Intent.ACTION_GET_CONTENT這個Action */
        intent.action = Intent.ACTION_GET_CONTENT
        /* 取得相片後返回本畫面 */
        startActivityForResult(intent, currentImageCode)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            context?.let {
                val uri = data?.data
                val cr = it.contentResolver
                try {
                    uri?.let {
                        val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri))
                        val drawable = BitmapDrawable(bitmap)
                        context?.let {
                            val file = File(getAbsolutePath(it, uri))
                            fileList.add(file)
//                            val requestBody =
//                                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//                            imageMap.put("images\"; filename=\"" + file.name, requestBody)
                        }

                        /* 將Bitmap設定到ImageView */
                        when (requestCode) {
                            ImageType.LEFT_IMAGE.value -> {
                                leftImageView.background = drawable
//                                imageByteArray[0] = Bitmap2Bytes(bitmap)
                            }
                            ImageType.MID_IMAGE.value -> {
                                midImageView.background = drawable
//                                imageByteArray[1] = Bitmap2Bytes(bitmap)
                            }
                            ImageType.RIGHT_IMAGE.value -> {
                                rightImageView.background = drawable
//                                imageByteArray[2] = Bitmap2Bytes(bitmap)
                            }
                            else -> {
                            }
                        }
                    }
                } catch (e: FileNotFoundException) {
                }
            }
        }
    }


    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        //觸摸的是EditText並且當前EditText可以滾動則將事件交給EditText處理；否則將事件交由其父類處理
        if (view.id == R.id.commodityDescriptionEditText && canVerticalScroll(
                commodityDescriptionEditText
            )
        ) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return false
    }

    /**
     * EditText豎直方向是否可以滾動
     * @param editText 需要判斷的EditText
     * @return true：可以滾動  false：不可以滾動
     */
    private fun canVerticalScroll(editText: EditText): Boolean {
        //滾動的距離
        val scrollY = editText.scrollY
        //控件內容的總高度
        val scrollRange = editText.layout.height
        //控件實際顯示的高度
        val scrollExtent =
            editText.height - editText.compoundPaddingTop - editText.compoundPaddingBottom
        //控件內容總高度與實際顯示高度的差值
        val scrollDifference = scrollRange - scrollExtent

        return if (scrollDifference == 0) {
            false
        } else scrollY > 0 || scrollY < scrollDifference - 1

    }


    private fun setRadioStatus(type: String) {
        selectType = type
        childRadioButton.isChecked = false
        travelRadioButton.isChecked = false
        hospitalRadioButton.isChecked = false
        elerictRadioButton.isChecked = false
        gameRadioButton.isChecked = false
        when (type) {
            SelectType.CHILD.value -> childRadioButton.isChecked = true
            SelectType.TRAVEL.value -> travelRadioButton.isChecked = true
            SelectType.HOSPITAL.value -> hospitalRadioButton.isChecked = true
            SelectType.ELECTRIC.value -> elerictRadioButton.isChecked = true
            SelectType.GAME.value -> gameRadioButton.isChecked = true
        }
    }

    private fun clearRadio() {
        selectType = ""
        childRadioButton.isChecked = false
        travelRadioButton.isChecked = false
        hospitalRadioButton.isChecked = false
        elerictRadioButton.isChecked = false
        gameRadioButton.isChecked = false
    }

    private fun upLoad() {
        if (fileList.isEmpty()) {
            Toast.makeText(context, "至少上傳一張照片", Toast.LENGTH_SHORT).show()
            return
        }
        if (commodityNameEditText.text.isNullOrEmpty()) {
            Toast.makeText(context, "商品名稱誤空白", Toast.LENGTH_SHORT).show()
            return
        }
        if (commodityRentEditText.text.isNullOrEmpty()) {
            Toast.makeText(context, "請輸入租金", Toast.LENGTH_SHORT).show()
            return
        }
        if (commodityDepositEditText.text.isNullOrEmpty()) {
            Toast.makeText(context, "請輸入押金", Toast.LENGTH_SHORT).show()
            return
        }
        if (commodityAddressEditText.text.isNullOrEmpty()) {
            Toast.makeText(context, "租借地址誤空白", Toast.LENGTH_SHORT).show()
            return
        }
        if (commodityDescriptionEditText.text.isNullOrEmpty()) {
            Toast.makeText(context, "商品描述誤空白", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectType.isNullOrEmpty()) {
            Toast.makeText(context, "請選擇商品種類", Toast.LENGTH_SHORT).show()
            return
        }
        fragmentManager?.let {
            loadingDialog.show(it, loadingDialog.tag)
        }

        var params = mutableMapOf<String, RequestBody>()
        //以下参数是伪代码，参数需要换成自己服务器支持的
        params["name"] = convertToRequestBody(commodityNameEditText.text.toString())
        params["category"] = convertToRequestBody(selectType)
        params["rent_amount"] = convertToRequestBody(commodityRentEditText.text.toString())
        params["deposit_amount"] = convertToRequestBody(commodityDepositEditText.text.toString())
        params["description"] = convertToRequestBody(commodityDescriptionEditText.text.toString())
        params["address"] = convertToRequestBody(commodityAddressEditText.text.toString())

        partList = filesToMultipartBodyParts(fileList)

        viewModel.postUploaad(
            params, partList!!
        )

    }

    fun Bitmap2Bytes(bm: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }


    private fun getAbsolutePath(context: Context, uri: Uri): String {
        val localContentResolver = context.contentResolver
        val localCursor = localContentResolver.query(uri, null, null, null, null)
        localCursor!!.moveToFirst()
        return localCursor.getString(localCursor.getColumnIndex("_data"))
    }

    private fun convertToRequestBody(param: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), param)
    }

    private fun filesToMultipartBodyParts(files: List<File>): ArrayList<MultipartBody.Part> {
        val parts = arrayListOf<MultipartBody.Part>()
        for (file in files) {
            val requestBody = RequestBody.create("image/png".toMediaTypeOrNull(), file)
            val part = MultipartBody.Part.createFormData("images", file.name, requestBody)
            parts.add(part)
        }
        return parts
    }

    private fun clearData() {
        clearRadio()
        fileList.clear()
        partList.clear()
        commodityNameEditText.setText("")
        commodityRentEditText.setText("")
        commodityDepositEditText.setText("")
        commodityDescriptionEditText.setText("")
        commodityAddressEditText.setText("")
        val drawable = ContextCompat.getDrawable(context!!,R.drawable.add_pic)
        leftImageView.background = drawable
        midImageView.background = drawable
        rightImageView.background = drawable
    }
}
