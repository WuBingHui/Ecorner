package com.anthony.ecorner.main.commodity.view

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.anthony.ecorner.R
import com.anthony.ecorner.main.commodity.adapter.CommodityAdapter
import kotlinx.android.synthetic.main.activity_commodity.*
import com.anthony.ecorner.main.commodity.ItemDecoration.SpaceItemDecoration
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.commodity.adapter.CommodityDetailAdapter


class CommodityActivity : BaseActivity() {
    private lateinit var commodityAdapter: CommodityAdapter


    companion object{
        val commodityActivity = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity)


        initView()


    }


    private fun initView() {
        typeLabel.text = intent.getStringExtra("TYPE")
        val column = 3
        val commodityGridLayoutManager = GridLayoutManager(this, column)
        commodityAdapter = CommodityAdapter(this)
        commodityAdapter.setOnItemClick(object : CommodityAdapter.SetItemClick{
            override fun onClick() {
                val intent = Intent()
                intent.setClass(this@CommodityActivity, CommodityDetailActivity::class.java)
                startActivity(intent)
            }
        })
        commodityRecyclerView.layoutManager = commodityGridLayoutManager
        commodityRecyclerView.addItemDecoration(SpaceItemDecoration(this.dp2px(20), column))
        commodityRecyclerView.adapter = commodityAdapter
    }
}
