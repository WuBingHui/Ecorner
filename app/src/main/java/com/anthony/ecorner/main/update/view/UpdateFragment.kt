package com.anthony.ecorner.main.update.view


import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anthony.ecorner.R
import android.content.Intent
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.FileNotFoundException
import android.graphics.drawable.BitmapDrawable
import android.view.MotionEvent
import android.widget.EditText
import com.csnt.android_sport.main.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class UpdateFragment : BaseFragment() ,View.OnTouchListener{


    override fun getData() {
    }


    companion object {
        const val LEFT_IMAGE = 0
        const val MID_IMAGE = 1
        const val RIGHT_IMAGE = 2

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

    }

    private fun setImage(currentImageCode: Int) {
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
                        /* 將Bitmap設定到ImageView */
                        when (requestCode) {
                            LEFT_IMAGE -> leftImageView.background = drawable
                            MID_IMAGE -> midImageView.background = drawable
                            RIGHT_IMAGE -> rightImageView.background = drawable
                        }
                    }
                } catch (e: FileNotFoundException) {
                }
            }
        }
    }


  override  fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        //觸摸的是EditText並且當前EditText可以滾動則將事件交給EditText處理；否則將事件交由其父類處理
        if (view.id == R.id.commodityDescriptionEditText && canVerticalScroll(commodityDescriptionEditText)) {
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
}
