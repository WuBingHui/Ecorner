package com.anthony.ecorner.dto.my_rent.response


data class MyRentDto(
    val error: String,
    val order: List<Order>,
    val result: Boolean
)

data class Order(
    val applicant: Applicant,
    val bank_account: String,
    val delivery_info: String,
    val description: String,
    val id: Int,
    val owner: Owner,
    val payment: String,
    val product: Product,
    val rent_at_begin: String,
    val rent_at_end: String,
    val shipping: String,
    val shipping_address: String,
    val status: Int,
    val store_number: String,
    val trading_location: String
)

data class Applicant(
    val address: String,
    val bank_code: String,
    val bank_number: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val updated_at: String,
    val username: String
)

data class Owner(
    val address: String,
    val bank_code: String,
    val bank_number: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val updated_at: String,
    val username: String
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