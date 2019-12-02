package com.anthony.ecorner.main.commodity.view

import android.app.DatePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.commodity.request.ApplyCommodityBo
import com.anthony.ecorner.dto.commodity.request.CollectBo
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.commodity.adapter.CommodityDetailAdapter
import com.anthony.ecorner.main.commodity.viewmodel.CommodityViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import kotlinx.android.synthetic.main.activity_commodity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommodityDetailActivity : BaseActivity() {

    private val loadingDialog = CustomLoadingDialog.newInstance()

    private val viewModel by viewModel<CommodityViewModel>()

    private var payment = ""
    private var shipping = ""
    private var trading_location = ""
    private var store_number = ""
    private var shipping_address = ""
    private var delivery_info = ""
    private var bank_account = ""


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
    private var startTime = 1
    private var endTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)


        initView()
        initViewModel()
        loadingDialog.show(supportFragmentManager, loadingDialog.tag)
        viewModel.getCommodityDetail(id)
    }

    private fun initView() {
        id = intent.getIntExtra("ID", 0)
        selfGetRadioBtn.setOnClickListener {
            setSendMethodRadioStatus(SendMethod.SELF_GET)
        }
        superCommercialRadioBtn.setOnClickListener {
            setSendMethodRadioStatus(SendMethod.SUPER_COMMERCIA)
        }
        homeDeliveryRadioBtn.setOnClickListener {
            setSendMethodRadioStatus(SendMethod.HOME_DELIVERY)
        }

        colletLabel.setOnClickListener {
            loadingDialog.show(supportFragmentManager, loadingDialog.tag)
            viewModel.postCollect(CollectBo(id))
        }

        transferRadioBtn.setOnClickListener { setPayMethodRadioStatus(PayMethod.TRANSFER) }
        cashOnDeliveryRadioBtn.setOnClickListener { setPayMethodRadioStatus(PayMethod.CASH_ON_DELIVERY) }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        startTimeLabel.setOnClickListener {
            val dataPickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                startTime = "$year${monthOfYear + 1}$dayOfMonth".toInt()
                startTimeLabel.text = "$year-${monthOfYear + 1}-$dayOfMonth"
            }, year, month, day)

            dataPickerDialog.show()
        }
        endTimeLabel.setOnClickListener {

            val dataPickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                endTime = "$year${monthOfYear + 1}$dayOfMonth".toInt()
                endTimeLabel.text = "$year-${monthOfYear + 1}-$dayOfMonth"
            }, year, month, day)

            dataPickerDialog.show()
        }

        rentBtn.setOnClickListener {
            if (startTimeLabel.text.isEmpty() || endTimeLabel.text.isEmpty()) {
                Toast.makeText(this, "請選擇時間!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (startTime > endTime) {
                Toast.makeText(this, "開始時間不可大於結束時間!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (shipping.isEmpty()) {
                Toast.makeText(this, "請選擇寄送方式", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (payment.isEmpty()) {
                Toast.makeText(this, "請選擇付款方式", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showConfirmAlerDialog()
        }
    }


    private fun setSendMethodRadioStatus(sendMethod: SendMethod) {
        superCommercialRadioBtn.isChecked = false
        selfGetRadioBtn.isChecked = false
        homeDeliveryRadioBtn.isChecked = false

        when (sendMethod) {
            SendMethod.SELF_GET -> {
                shipping = "自提自取"

                selfGetRadioBtn.isChecked = true
                selfGetEditText.visibility = View.GONE
                superCommercialEditText.visibility = View.GONE
                homeDeliveryEditText.visibility = View.GONE
            }
            SendMethod.HOME_DELIVERY -> {
                shipping = "超商店到店"

                homeDeliveryRadioBtn.isChecked = true

                selfGetEditText.visibility = View.GONE
                superCommercialEditText.visibility = View.GONE
                homeDeliveryEditText.visibility = View.VISIBLE
            }
            SendMethod.SUPER_COMMERCIA -> {
                shipping = "宅配"

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
                payment = "貨到付款"

                cashOnDeliveryRadioBtn.isChecked = true
                cashOnDeliveryEditText.visibility = View.VISIBLE
                transferEditText.visibility = View.GONE
                transferNameEditText.visibility = View.GONE
                transferPhoneEditText.visibility = View.GONE
            }
            PayMethod.TRANSFER -> {
                payment = "轉帳"

                transferRadioBtn.isChecked = true
                cashOnDeliveryEditText.visibility = View.GONE
                transferEditText.visibility = View.GONE
                transferNameEditText.visibility = View.VISIBLE
                transferPhoneEditText.visibility = View.VISIBLE
            }

        }
    }

    private fun initViewModel() {
        viewModel.onCommodityDetail.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.product?.let {
                        it.images?.let { images ->
                            val commodityDetailAdapter = CommodityDetailAdapter(this)
                            commdoityViewPager.adapter = commodityDetailAdapter
                            commodityDetailAdapter.setData(images)
                        }
                        commodityTitleLabel.text = it.name
                        amountLabel.text =
                                String.format(getString(R.string.amount), it.rent_amount.toString())
                        depositLabel.text =
                                String.format(getString(R.string.amount), it.deposit_amount.toString())
                        descriptionLabel.text = it.description
                        rentAddressLabel.text = it.address
                    }
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })
        viewModel.onApplyCommodity.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "申請成功!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })

        viewModel.onCollect.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show()
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })
    }

    private fun showConfirmAlerDialog() {
        AlertDialog.Builder(this)
                .setMessage(descriptionLabel.text)
                .setTitle(commodityTitleLabel.text)
                .setPositiveButton("申請") { _, _ ->
                    loadingDialog.show(supportFragmentManager, loadingDialog.tag)
                    viewModel.postApply(ApplyCommodityBo(id,
                            startTimeLabel.text.toString(),
                            endTimeLabel.text.toString(),
                            payment,
                            shipping,
                            messageEditText.text.toString(),
                            selfGetEditText.text.toString(),
                            superCommercialEditText.text.toString(),
                            homeDeliveryEditText.text.toString(),
                            cashOnDeliveryEditText.text.toString(),
                            "${transferEditText.text}/${transferNameEditText.text}/${transferPhoneEditText.text}"))

                }
                .setNeutralButton("取消", null)
                .create()
                .show()
    }
}
