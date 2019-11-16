package com.anthony.ecorner.dto.upload.response

data class UploadDto(
    val error:String,
    val product: Product,
    val result: Boolean
)

data class Product(
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