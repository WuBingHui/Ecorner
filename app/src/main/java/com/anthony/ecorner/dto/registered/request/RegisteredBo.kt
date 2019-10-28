package com.anthony.ecorner.dto.registered.request

data class RegisteredBo(
    val username: String,
    val password: String,
    val name: String,
    val phone_number: String,
    val address: String
)