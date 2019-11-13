package com.anthony.ecorner.dto.commodity.response

data class UniqueCommodityDto(
    val error: String,
    val products: List<Product>?,
    val result: Boolean,
    val total: Int
)

data class Product(
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