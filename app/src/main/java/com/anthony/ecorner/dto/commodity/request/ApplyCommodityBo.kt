package com.anthony.ecorner.dto.commodity.request
data class ApplyCommodityBo(
    val product_id: Int,
    val rent_at_begin: String,
    val rent_at_end: String
)