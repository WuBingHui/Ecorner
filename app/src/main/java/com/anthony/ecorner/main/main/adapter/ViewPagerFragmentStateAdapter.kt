package com.anthony.ecorner.main.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerFragmentStateAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){


    var fragmentList = ArrayList<Fragment>()


    fun  setFragments(fragmentList:ArrayList<Fragment>){

        this.fragmentList = fragmentList

        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}