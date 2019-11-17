package com.anthony.ecorner.dto.commodity.request

import android.accounts.AuthenticatorDescription

data class ApplyCommodityBo(
    val product_id: Int,
    val rent_at_begin: String,
    val rent_at_end: String,
    val payment:String,
    val shipping:String,
    val description: String,
    val  trading_location: String,
    val   store_number: String,
    val  shipping_address: String,
    val delivery_info: String,
    val  bank_account: String
)