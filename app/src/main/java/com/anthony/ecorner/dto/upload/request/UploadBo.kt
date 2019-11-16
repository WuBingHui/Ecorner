package com.anthony.ecorner.dto.upload.request

import java.io.File

data class UploadBo(
    val address: String,
    val category: String,
    val deposit_amount: Int,
    val description: String,
    val name: String,
    val rent_amount: Int
)