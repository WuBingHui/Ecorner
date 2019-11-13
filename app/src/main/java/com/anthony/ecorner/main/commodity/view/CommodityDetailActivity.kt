package com.anthony.ecorner.main.commodity.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.commodity.adapter.CommodityDetailAdapter
import com.anthony.ecorner.main.commodity.viewmodel.CommodityViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import kotlinx.android.synthetic.main.activity_commodity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommodityDetailActivity : BaseActivity() {

    private val loadingDialog = CustomLoadingDialog.newInstance()

    private val viewModel by viewModel<CommodityViewModel>()
    enum class SendMethod {
        SELF_GET,
        SUPER_COMMERCIA,
        HOME_DELIVERY
    }

    enum class PayMethod {
        CASH_ON_DELIVERY,
        TRANSFER
    }

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)


        initView()
        initViewModel()
        loadingDialog.show(supportFragmentManager,loadingDialog.tag)
        viewModel.getCommodityDetail(id)
    }

    private fun initView() {
        id = intent.getIntExtra("ID",0)
        selfGetRadioBtn.setOnClickListener {
            setSendMethodRadioStatus(SendMethod.SELF_GET)
        }
        superCommercialRadioBtn.setOnClickListener {
            setSendMethodRadioStatus(SendMethod.SUPER_COMMERCIA)
        }
        homeDeliveryRadioBtn.setOnClickListener {
            setSendMethodRadioStatus(SendMethod.HOME_DELIVERY)
        }

        transferRadioBtn.setOnClickListener { setPayMethodRadioStatus(PayMethod.TRANSFER) }
        cashOnDeliveryRadioBtn.setOnClickListener { setPayMethodRadioStatus(PayMethod.CASH_ON_DELIVERY) }


    }


    private fun setSendMethodRadioStatus(sendMethod: SendMethod) {
        superCommercialRadioBtn.isChecked = false
        selfGetRadioBtn.isChecked = false
        homeDeliveryRadioBtn.isChecked = false

        when (sendMethod) {
            SendMethod.SELF_GET -> {
                selfGetRadioBtn.isChecked = true
                selfGetEditText.visibility = View.VISIBLE
                superCommercialEditText.visibility = View.GONE
                homeDeliveryEditText.visibility = View.GONE
            }
            SendMethod.HOME_DELIVERY -> {
                homeDeliveryRadioBtn.isChecked = true

                selfGetEditText.visibility = View.GONE
                superCommercialEditText.visibility = View.GONE
                homeDeliveryEditText.visibility = View.VISIBLE
            }
            SendMethod.SUPER_COMMERCIA -> {
                superCommercialRadioBtn.isChecked = true
                selfGetEditText.visibility = View.GONE
                superCommercialEditText.visibility = View.VISIBLE
                homeDeliveryEditText.visibility = View.GONE
            }
        }
    }

    private fun setPayMethodRadioStatus(payMethod: PayMethod) {
        cashOnDeliveryRadioBtn.isChecked = false
        transferRadioBtn.isChecked = false

        when (payMethod) {
            PayMethod.CASH_ON_DELIVERY -> {
                cashOnDeliveryRadioBtn.isChecked = true
                cashOnDeliveryEditText.visibility = View.VISIBLE
                transferEditText.visibility = View.GONE
            }
            PayMethod.TRANSFER -> {
                transferRadioBtn.isChecked = true
                cashOnDeliveryEditText.visibility = View.GONE
                transferEditText.visibility = View.VISIBLE
            }

        }
    }

    private fun initViewModel(){
        viewModel.onCommodityDetail.observe(this, Observer { dto->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.product?.let {
                        it.images?.let {images->
                            val commodityDetailAdapter = CommodityDetailAdapter(this)
                            commdoityViewPager.adapter = commodityDetailAdapter
                            commodityDetailAdapter.setData(images)
                        }
                        commodityTitleLabel.text = it.name
                        amountLabel.text = String.format(getString(R.string.amount),it.rent_amount.toString())
                        depositLabel.text =String.format(getString(R.string.amount),it.deposit_amount.toString())
                        descriptionLabel.text =it.description
                        rentAddressLabel.text =it.address
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
