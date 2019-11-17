package com.anthony.ecorner.main.my_rent.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.my_rent.response.Order
import com.anthony.ecorner.dto.my_rent.response.ProductX
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.anthony.ecorner.main.my_rent.adapter.MyRentAdapter
import com.anthony.ecorner.main.my_rent.viewmodel.MyRentViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_my_rent.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyRentActivity : BaseActivity() {

    private val viewModel by viewModel<MyRentViewModel>()
    private val loadingDialog = CustomLoadingDialog.newInstance()
    private var myRentAdapter: MyRentAdapter? = null


    enum class TabType(val value: Int) {
        RENT_REUQEST(0),
        MY_RENT(1),
        MY_LOAD(2)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_rent)


        initView()
        initViewModel()

    }

    private fun initView() {

        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL


        myRentAdapter = MyRentAdapter(this)

        myRentAdapter!!.setOnItemClick(object : MyRentAdapter.SetItemClick {
            override fun onClick(dto: Any, type: String) {
                when (type) {
                    MyRentAdapter.Type.APPLICANT.value -> ReplyApplicationActivity.start(this@MyRentActivity, dto as Order)
                    MyRentAdapter.Type.OWNER.value -> {
                    }
                    MyRentAdapter.Type.UPLOAD.value -> {
                        val intent = Intent()
                        intent.putExtra("ID", (dto as ProductX).id)
                        intent.setClass(this@MyRentActivity, CommodityDetailActivity::class.java)
                        startActivity(intent)
                    }
                }

            }

        })

        rentRecyclerView.layoutManager = linearLayoutManager

        rentRecyclerView.adapter = myRentAdapter


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadingDialog.show(supportFragmentManager, loadingDialog.tag)
                when (tab?.position) {
                    TabType.RENT_REUQEST.value -> viewModel.getRentApply()
                    TabType.MY_RENT.value -> viewModel.getMyRent()
                    TabType.MY_LOAD.value -> viewModel.getMyUpload()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadingDialog.show(supportFragmentManager, loadingDialog.tag)
        when (tabLayout.selectedTabPosition) {
            TabType.RENT_REUQEST.value -> viewModel.getRentApply()
            TabType.MY_RENT.value -> viewModel.getMyRent()
            TabType.MY_LOAD.value -> viewModel.getMyUpload()
        }
    }

    private fun initViewModel() {

        viewModel.onMyRent.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        myRentAdapter?.setData(it.order, MyRentAdapter.Type.OWNER.value)
                    }
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })

        viewModel.onRentApply.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        myRentAdapter?.setData(it.order, MyRentAdapter.Type.APPLICANT.value)
                    }
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })

        viewModel.onMyUpload.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        myRentAdapter?.setData(it.products, MyRentAdapter.Type.UPLOAD.value)
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
