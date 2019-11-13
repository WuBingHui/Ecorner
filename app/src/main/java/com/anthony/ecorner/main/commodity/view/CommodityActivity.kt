package com.anthony.ecorner.main.commodity.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.main.commodity.adapter.CommodityAdapter
import kotlinx.android.synthetic.main.activity_commodity.*
import com.anthony.ecorner.main.commodity.ItemDecoration.SpaceItemDecoration
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.commodity.viewmodel.CommodityViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class CommodityActivity : BaseActivity() {
    private lateinit var commodityAdapter: CommodityAdapter
    private val loadingDialog = CustomLoadingDialog.newInstance()

    private val viewModel by viewModel<CommodityViewModel>()
    private  var type:String =""
    companion object {
        val commodityActivity = this
        const val ID="ID"

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity)


        initView()
        initViewModel()
        loadingDialog.show(supportFragmentManager,loadingDialog.tag)
        viewModel.getUniqueCommodity(type,1,100)
    }


    private fun initView() {

        typeLabel.text = intent.getStringExtra("TYPE_NAME")
        type = intent.getStringExtra("TYPE")
        val column = 3
        val commodityGridLayoutManager = GridLayoutManager(this, column)
        commodityAdapter = CommodityAdapter(this)
        commodityAdapter.setOnItemClick(object : CommodityAdapter.SetItemClick {
            override fun onClick(id: Int) {
                val intent = Intent()
                intent.putExtra("ID",id)
                intent.setClass(this@CommodityActivity, CommodityDetailActivity::class.java)
                startActivity(intent)
            }

        })
        commodityRecyclerView.layoutManager = commodityGridLayoutManager
        commodityRecyclerView.addItemDecoration(SpaceItemDecoration(this.dp2px(20), column))
        commodityRecyclerView.adapter = commodityAdapter
    }

    private fun initViewModel(){
        viewModel.onUniqueCommodity.observe(this, Observer {dto ->
                when (dto.status) {
                    Status.SUCCESS -> {
                        dto.data?.products?.let {
                            commodityAdapter.setData(it)
                        }
                    }
                    Status.FAILED -> {
                        Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                    }
                }
            loadingDialog.dismiss()
        })



    }


}
