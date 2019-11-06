package com.anthony.ecorner.main.main.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.anthony.ecorner.R
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.home.view.HomeFragment
import com.anthony.ecorner.main.main.adapter.ViewPagerFragmentStateAdapter
import com.anthony.ecorner.main.message.view.MessageFragment
import com.anthony.ecorner.main.personal.view.PersonalFragment
import com.anthony.ecorner.main.update.view.UpdateFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var exitTime: Long = 0

    companion object{
        val activity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initBottomNavigationView()


    }

    enum class CurrentPages(val value: Int) {
        HOME(0), UPDATE(1), MESSAGE(2), PERSONAL(3)
    }


    private fun initView() {

        val fragmentList = ArrayList<Fragment>()

        fragmentList.add(HomeFragment())
        fragmentList.add(UpdateFragment())
        fragmentList.add(MessageFragment())
        fragmentList.add(PersonalFragment())
        initViewPager(fragmentList)
    }

    private fun initViewPager(fragmentList: ArrayList<Fragment>) {

        val viewPagerFragmentStateAdapter = ViewPagerFragmentStateAdapter(this)

        viewPager.adapter = viewPagerFragmentStateAdapter

        viewPagerFragmentStateAdapter.setFragments(fragmentList)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }

        })

    }

    private fun initBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.home -> {
                    viewPager.currentItem = CurrentPages.HOME.value
                    true
                }
                R.id.update -> {
                    viewPager.currentItem = CurrentPages.UPDATE.value
                    true
                }
                R.id.message -> {
                    viewPager.currentItem = CurrentPages.MESSAGE.value
                    true
                }
                R.id.personal -> {
                    viewPager.currentItem = CurrentPages.PERSONAL.value
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            moveTaskToBack(true)
        }
    }


}

