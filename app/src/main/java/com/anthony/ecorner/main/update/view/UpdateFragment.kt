package com.anthony.ecorner.main.update.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

import com.anthony.ecorner.R

/**
 * A simple [Fragment] subclass.
 */
class UpdateFragment : Fragment() {
    private var banner: ViewPager2? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        banner = view.findViewById(R.id.banner)
        initBanner()

        return view
    }

    private fun initBanner() {
        context?.let {
            val imgList = mutableListOf(
                ContextCompat.getDrawable(it, R.drawable.banner_1),
                ContextCompat.getDrawable(it, R.drawable.banner_2),
                ContextCompat.getDrawable(it, R.drawable.banner_3)
            )
//            val bannerAdapter = BannerAdapter(it)
//            banner?.adapter = bannerAdapter
//            bannerAdapter.setImages(imgList)
        }
    }


}
