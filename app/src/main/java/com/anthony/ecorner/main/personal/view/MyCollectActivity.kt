package com.anthony.ecorner.main.personal.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.my_rent.request.DeleteBo
import com.anthony.ecorner.dto.my_rent.response.ProductX
import com.anthony.ecorner.dto.personal.resquest.DeleteMyCollectBo
import com.anthony.ecorner.dto.personal.resquest.ProfileBo
import com.anthony.ecorner.koin.Properties
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.anthony.ecorner.main.my_rent.adapter.MyRentAdapter
import com.anthony.ecorner.main.personal.view.adapter.MyCollectAdapter
import com.anthony.ecorner.main.personal.view.viewModel.PersonalViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import kotlinx.android.synthetic.main.activity_my_collect.*
import kotlinx.android.synthetic.main.activity_my_rent.*
import kotlinx.android.synthetic.main.activity_personal_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyCollectActivity : AppCompatActivity() {
    private val viewmodel by viewModel<PersonalViewModel>()
    private var myCollectAdapter:MyCollectAdapter? = null
    private val loadingDialog = CustomLoadingDialog.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collect)

        initView()
        initViewModel()
    }

    private fun initView() {

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
         myCollectAdapter = MyCollectAdapter(this)
        myCollectRecyclerView.layoutManager = linearLayoutManager
        myCollectRecyclerView.adapter = myCollectAdapter

        myCollectAdapter!!.setOnItemClick(object :MyCollectAdapter.SetItemClick{
            override fun onClick(id:Int) {
                val intent = Intent()
                intent.putExtra("ID", id)
                intent.setClass(this@MyCollectActivity, CommodityDetailActivity::class.java)
                startActivity(intent)
            }

        })
        myCollectAdapter!!.setOnItemLongClick(object :MyCollectAdapter.SetItemLongClick{
            override fun onClick(id:Int) {
                delete(id)
            }
        })
        loadingDialog.show(supportFragmentManager, loadingDialog.tag)
        viewmodel.getMyCollect()
    }

    private fun initViewModel(){
        viewmodel.onMyCollect.observe(this, Observer { dto->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.data?.let {
                        myCollectAdapter?.setData(it)
                    }
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })

        viewmodel.onDeleteMyCollect.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    viewmodel.getMyCollect()
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })

    }


    private fun delete(id:Int){

        AlertDialog.Builder(this)
            .setMessage("確定要刪除收藏?")
            .setTitle("刪除收藏")
            .setPositiveButton("刪除") { _, _ ->
                loadingDialog.show(supportFragmentManager, loadingDialog.tag)
                viewmodel.postDeleteMyCollect(DeleteMyCollectBo((id)))
            }
            .setNeutralButton("取消", null)
            .create()
            .show()
    }
}
