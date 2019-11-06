package com.anthony.ecorner.dto.home.reponse

data class CommodityDto(
    val child: List<Child>,
    val electric: List<Electric>,
    val game: List<Game>,
    val hospital: List<Hospital>,
    val result: Boolean,
    val error: String,
    val travel: List<Travel>
)

data class Child(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val id: Int,
    val images: List<String>,
    val name: String,
    val rent_amount: Int
)

data class Electric(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val id: Int,
    val images: List<String>,
    val name: String,
    val rent_amount: Int
)

data class Game(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val id: Int,
    val images: List<String>,
    val name: String,
    val rent_amount: Int
)

data class Hospital(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val id: Int,
    val images: List<String>,
    val name: String,
    val rent_amount: Int
)

data class Travel(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val id: Int,
    val images: List<String>,
    val name: String,
    val rent_amount: Int
)