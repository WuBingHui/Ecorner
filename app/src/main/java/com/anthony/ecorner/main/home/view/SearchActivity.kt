package com.anthony.ecorner.main.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.home.reponse.Product
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.main.commodity.ItemDecoration.SpaceItemDecoration
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.anthony.ecorner.main.home.view.adapter.SearchAdapter
import com.anthony.ecorner.main.home.view.viewModel.HomeViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_search.*
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
        initSwipe()
        initViewModel()
        val intent = intent
        loadingDialog.show(supportFragmentManager, loadingDialog.tag)
        viewModel.getProductSearch(intent.getStringExtra(KEYWORD),intent.getStringExtra(CATEGORY))

    }

    private fun initSwipe() {

        laySwipe.setOnRefreshListener {
            Handler().postDelayed({
                if (laySwipe.isRefreshing) laySwipe.isRefreshing = false

            }, 1000)
        }

        laySwipe.setColorSchemeResources(R.color.winterNevaDark, R.color.winterNevaDark)
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
        searchRecyclerView.setHasFixedSize(true)
        searchRecyclerView.layoutManager = gridLayoutManager
        searchRecyclerView.addItemDecoration(SpaceItemDecoration(this.dp2px(20), column))
        searchRecyclerView.adapter = searchAdapter
        searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // 查看源碼可知State有三種狀態：SCROLL_STATE_IDLE（靜止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滾動靜止時才加載圖片資源，極大提升流暢度
                    searchAdapter.isScrolling(false);
                    //通知adapter恢复getview中的图下载
                    if(Glide.with(this@SearchActivity).isPaused)Glide.with(this@SearchActivity).resumeRequests()
                } else{
                    searchAdapter.isScrolling(true);
                }

                super.onScrollStateChanged(recyclerView, newState)

            }
        })
    }

    private fun initViewModel(){

        viewModel.onSearch.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        searchAdapter.submitList(it.data)
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
