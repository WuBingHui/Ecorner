package com.anthony.ecorner.dto.message.response


data class MessageDto(
    val error:String,
    val apply: List<Apply>,
    val leases: List<Lease>,
    val result: Boolean
)

data class Apply(
    val created_at: String,
    val description: String,
    val id: Int,
    val news_type: String,
    val updated_at: String,
    val user_id: Int
)

data class Lease(
    val created_at: String,
    val description: String,
    val id: Int,
    val news_type: String,
    val updated_at: String,
    val user_id: Int
)