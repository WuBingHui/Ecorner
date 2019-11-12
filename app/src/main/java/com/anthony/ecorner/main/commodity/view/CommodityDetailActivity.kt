package com.anthony.ecorner.main.commodity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.anthony.ecorner.R
import com.anthony.ecorner.main.commodity.adapter.CommodityDetailAdapter
import kotlinx.android.synthetic.main.activity_commodity_detail.*

class CommodityDetailActivity : AppCompatActivity() {


    enum class SendMethod {
        SELF_GET,
        SUPER_COMMERCIA,
        HOME_DELIVERY
    }

    enum class PayMethod {
        CASH_ON_DELIVERY,
        TRANSFER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)


        initView()

    }

    private fun initView() {

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

        val imgArray = arrayOf(
            ContextCompat.getDrawable(this, R.drawable.banner_1),
            ContextCompat.getDrawable(this, R.drawable.banner_2),
            ContextCompat.getDrawable(this, R.drawable.banner_3)
        )

        var commodityDetailAdapter = CommodityDetailAdapter(this)
        commdoityViewPager.adapter = commodityDetailAdapter
        commodityDetailAdapter.setData(imgArray)
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
}
