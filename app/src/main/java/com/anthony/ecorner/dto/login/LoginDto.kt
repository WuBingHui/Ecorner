package com.anthony.ecorner.dto.login

data class LoginDto(
    val error:String,
    val result: Boolean,
    val user: User
)
data class User(
    val id: Int,
    val name: String,
    val username: String
)