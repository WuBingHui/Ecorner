package com.anthony.ecorner.dto.personal.response

data class ProfileDto(
    val error: String,
    val result: Boolean,
    val user: User
)

data class User(
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