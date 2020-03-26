package com.anthony.ecorner.main.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anthony.ecorner.main.main.view.MainActivity

class ViewPagerFragmentStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    private val DIFF_CALLBACK = object :DiffUtil.ItemCallback<Fragment>(){
        override fun areItemsTheSame(oldItem: Fragment, newItem: Fragment): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Fragment, newItem: Fragment): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)


    fun  setFragments(fragmentList:ArrayList<Fragment>){

        differ.submitList(fragmentList)

    }

    fun refreshPage( Pposition: MainActivity.CurrentPages){
        notifyItemChanged(Pposition.value)
    }

    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return differ.currentList[position]

    }

}