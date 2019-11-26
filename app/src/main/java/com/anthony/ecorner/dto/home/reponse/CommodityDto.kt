package com.anthony.ecorner.dto.home.reponse

data class CommodityDto(
    val categories: Categories,
    val error: String,
    val result: Boolean
)

data class Categories(
    val child: List<Product>?,
    val electric: List<Product>?,
    val game: List<Product>?,
    val hospital: List<Product>?,
    val travel: List<Product>?
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

