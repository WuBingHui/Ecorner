package com.anthony.ecorner.main.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){


    var fragmentList = ArrayList<Fragment>()


    fun  setFragments(fragmentList:ArrayList<Fragment>){

        this.fragmentList = fragmentList

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]

    }

}