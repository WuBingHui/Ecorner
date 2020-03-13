package com.anthony.ecorner.main.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.main.commodity.ItemDecoration.SpaceItemDecoration
import com.anthony.ecorner.main.commodity.adapter.CommodityAdapter
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.anthony.ecorner.main.home.view.adapter.SearchAdapter
import com.anthony.ecorner.main.home.view.viewModel.HomeViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel by viewModel<HomeViewModel>()
    private val loadingDialog = CustomLoadingDialog.newInstance()
    companion object {

        const val KEYWORD = "KEYWORD"
        const val CATEGORY = "CATEGORY"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
        initViewModel()
        val intent = intent
        loadingDialog.show(supportFragmentManager, loadingDialog.tag)
        viewModel.getProductSearch(intent.getStringExtra(KEYWORD),intent.getStringExtra(CATEGORY))

    }

    private fun initView(){

        val column = 3
        val gridLayoutManager = GridLayoutManager(this, column)
        searchAdapter = SearchAdapter()
        searchAdapter.setOnItemClick(object : SearchAdapter.SetItemClick {
            override fun onClick(id: Int) {
                val intent = Intent()
                intent.putExtra("ID",id)
                intent.setClass(this@SearchActivity, CommodityDetailActivity::class.java)
                startActivity(intent)
            }
        })
        searchRecyclerView.layoutManager = gridLayoutManager
        searchRecyclerView.addItemDecoration(SpaceItemDecoration(this.dp2px(20), column))
        searchRecyclerView.adapter = searchAdapter
    }

    private fun initViewModel(){

        viewModel.onSearch.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        searchAdapter.setData(it.data)
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
