package com.anthony.ecorner.dto.personal.resquest

data class UpdateProfileBo(
    val address: String,
    val bank_code: String,
    val bank_number: String,
    val name: String
)