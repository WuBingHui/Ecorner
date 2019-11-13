package com.anthony.ecorner.dto.home.reponse

data class CommodityDto(
    val categories: Categories,
    val error: String,
    val result: Boolean
)

data class Categories(
    val child: List<Child>?,
    val electric: List<Electric>?,
    val game: List<Game>?,
    val hospital: List<Hospital>?,
    val travel: List<Travel>?
)

data class Child(
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

data class Electric(
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

data class Game(
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

data class Hospital(
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

data class Travel(
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