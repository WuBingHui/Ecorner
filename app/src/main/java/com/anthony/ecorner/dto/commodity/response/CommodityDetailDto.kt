package com.anthony.ecorner.dto.commodity.response

data class CommodityDetailDto(
    val error: String,
    val product: ProductX,
    val result: Boolean
)

data class ProductX(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val id: Int,
    val images: List<String>?,
    val name: String,
    val rent_amount: Int,
    val status: Int
)