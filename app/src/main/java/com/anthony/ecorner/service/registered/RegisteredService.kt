package com.anthony.ecorner.service.registered

import com.anthony.ecorner.dto.registered.RegisteredDto
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisteredService {

    /**
     * 註冊
     */
    @POST("api/auth/register")
    fun postRegistered(@Query("username") username: String,
                  @Query("password") password: String,
                  @Query("name") name: String,
                  @Query("phone_number") phone: String,
                  @Query("address") address: String): Single<RegisteredDto>



}