package com.anthony.ecorner.service.registered

import com.anthony.ecorner.dto.registered.request.RegisteredBo
import com.anthony.ecorner.dto.registered.response.RegisteredDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisteredService {

    /**
     * 註冊
     */
    @POST("api/auth/register")
    fun postRegistered(@Body registeredBo: RegisteredBo): Single<RegisteredDto>



}