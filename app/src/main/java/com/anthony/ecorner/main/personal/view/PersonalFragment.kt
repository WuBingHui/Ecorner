package com.anthony.ecorner.main.personal.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.anthony.ecorner.R

/**
 * A simple [Fragment] subclass.
 */
class PersonalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal, container, false)
        initView(view)
        return view
    }

    private fun initView(view:View){

        val personal = view.findViewById<View>(R.id.personal)
        val collect = view.findViewById<View>(R.id.collect)
        val rent = view.findViewById<View>(R.id.rent)
        val setting = view.findViewById<View>(R.id.setting)
        val about = view.findViewById<View>(R.id.about)
        val logout = view.findViewById<View>(R.id.logout)

        val personalImg = personal.findViewById<ImageView>(R.id.profileImg)
        val collectImg = collect.findViewById<ImageView>(R.id.profileImg)
        val rentImg = rent.findViewById<ImageView>(R.id.profileImg)
        val settingImg = setting.findViewById<ImageView>(R.id.profileImg)
        val aboutImg = about.findViewById<ImageView>(R.id.profileImg)
        val logoutImg = logout.findViewById<ImageView>(R.id.profileImg)


        context?.let {
            personalImg.background = ContextCompat.getDrawable(it,R.drawable.personal)
            collectImg.background = ContextCompat.getDrawable(it,R.drawable.collect)
            rentImg.background = ContextCompat.getDrawable(it,R.drawable.rent)
            settingImg.background = ContextCompat.getDrawable(it,R.drawable.setting)
            aboutImg.background = ContextCompat.getDrawable(it,R.drawable.about)
            logoutImg.background = ContextCompat.getDrawable(it,R.drawable.logout)
        }

        val personalLabel = personal.findViewById<TextView>(R.id.profileLabel)
        val collectLabel = collect.findViewById<TextView>(R.id.profileLabel)
        val rentLabel = rent.findViewById<TextView>(R.id.profileLabel)
        val settingLabel = setting.findViewById<TextView>(R.id.profileLabel)
        val aboutLabel = about.findViewById<TextView>(R.id.profileLabel)
        val logoutLabel = logout.findViewById<TextView>(R.id.profileLabel)

        personalLabel.text = getString(R.string.person)
        collectLabel.text = getString(R.string.collect)
        rentLabel.text = getString(R.string.rent)
        settingLabel.text = getString(R.string.setting)
        aboutLabel.text = getString(R.string.about)
        logoutLabel.text = getString(R.string.logout)

    }


}
