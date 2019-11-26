package com.anthony.ecorner.dto.home.reponse

data class SearchDto(
    val `data`: List<Product>,
    val error: String,
    val result: Boolean
)

