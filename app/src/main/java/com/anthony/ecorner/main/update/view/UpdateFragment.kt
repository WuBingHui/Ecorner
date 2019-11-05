package com.anthony.ecorner.main.update.view


import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2

import com.anthony.ecorner.R
import android.content.Intent
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.FileNotFoundException


/**
 * A simple [Fragment] subclass.
 */
class UpdateFragment : Fragment() {


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
                        /* 將Bitmap設定到ImageView */
                        when (requestCode) {
                            LEFT_IMAGE -> leftImageView.setImageBitmap(bitmap)
                            MID_IMAGE -> midImageView.setImageBitmap(bitmap)
                            RIGHT_IMAGE -> rightImageView.setImageBitmap(bitmap)
                        }
                    }
                } catch (e: FileNotFoundException) {
                }
            }
        }
    }


}
