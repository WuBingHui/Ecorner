package com.anthony.ecorner.dto.home.reponse

data class CommodityDto(
    val categories: Categories,
    val error: String,
    val result: Boolean
)

data class Categories(
    val child: MutableList<Product>?,
    val electric: MutableList<Product>?,
    val game: MutableList<Product>?,
    val hospital: MutableList<Product>?,
    val travel: MutableList<Product>?
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
    val status: Int,
    var isLoaded:Boolean = false
)

