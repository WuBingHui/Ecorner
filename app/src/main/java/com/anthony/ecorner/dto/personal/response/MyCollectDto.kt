package com.anthony.ecorner.dto.personal.response

data class MyCollectDto(
    val data: List<Data>,
    val error: String,
    val result: Boolean
)

data class Data(
    val collet_id: Int,
    val product_info: ProductInfo
)

data class ProductInfo(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val id: Int,
    val images: List<String>,
    val name: String,
    val rent_amount: Int,
    val status: Int
)