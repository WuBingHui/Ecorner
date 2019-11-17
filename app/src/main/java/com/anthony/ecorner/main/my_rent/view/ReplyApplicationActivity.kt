package com.anthony.ecorner.main.my_rent.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.my_rent.request.ReplyApplicantBo
import com.anthony.ecorner.dto.my_rent.response.Order
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.my_rent.viewmodel.MyRentViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_reply_application.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReplyApplicationActivity : BaseActivity() {

    private val loadingFragment = CustomLoadingDialog.newInstance()

    private val viewModel by viewModel<MyRentViewModel>()

    companion object {
        private var order: Order? = null
        fun start(activity: Activity, order: Order) {
            this.order = order
            val intent = Intent()
            intent.setClass(activity, ReplyApplicationActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reply_application)

        initVIew()
        initViewModel()


    }

    private fun initVIew() {


        agreeBtn.setOnClickListener {
            loadingFragment.show(supportFragmentManager, loadingFragment.tag)
            viewModel.postReplyApplicant(ReplyApplicantBo(order?.product?.id!!,1))
        }
        rejectBtn.setOnClickListener {
            loadingFragment.show(supportFragmentManager, loadingFragment.tag)
            viewModel.postReplyApplicant(ReplyApplicantBo(order?.product?.id!!,2))
        }

        order?.let {
            applyTimeLabel.text = "${it.rent_at_begin.substring(0,10)} - ${it.rent_at_end.substring(0,10)}"
            applyLabel.text = it.applicant.name
            payLabel.text = "${it.payment}  ${getPaymentContent(it)}"
            sendLabel.text = "${it.shipping}  ${getShippingContent(it)}"
            remarkLabel.text = it.description
            commodityNameLabel.text =it.product.name
            commodityAmountLabel.text =it.product.description
            Glide.with(this)
                .load(it.product.images[0])
                .placeholder(R.drawable.mobile)
                .into(   commodityImg)

        }

    }


    private fun initViewModel() {
        viewModel.onReplyApplicant.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "已回覆", Toast.LENGTH_SHORT).show()
                    finish()
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingFragment.dismiss()
        })
    }

    private fun getPaymentContent(order:Order):String{
        if(order.bank_account.isNotEmpty()){
            return order.bank_account
        }
        if(order.delivery_info.isNotEmpty()){
            return order.delivery_info
        }
        return ""
    }

    private fun getShippingContent(order:Order):String{
        if(order.trading_location.isNotEmpty()){
            return order.trading_location
        }
        if(order.store_number.isNotEmpty()){
            return order.store_number
        }
        if(order.shipping_address.isNotEmpty()){
            return order.shipping_address
        }
        return ""
    }


}
