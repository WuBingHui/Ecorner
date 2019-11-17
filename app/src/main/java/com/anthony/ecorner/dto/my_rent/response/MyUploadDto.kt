package com.anthony.ecorner.dto.my_rent.response

data class MyUploadDto(
    val error: String,
    val products: List<ProductX>,
    val result: Boolean,
    val total: Int
)

data class ProductX(
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