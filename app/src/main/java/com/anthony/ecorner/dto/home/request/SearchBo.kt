package com.anthony.ecorner.dto.home.request

data class SearchBo(
    val keyword: String,
    val category: String="all"
)