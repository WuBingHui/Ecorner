package com.anthony.ecorner.service

import com.anthony.ecorner.dto.login.LoginDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    /**
     * 登入
     */
    @POST("api/auth/login")
    fun postLogin(@Body username: String,@Body password: String): Single<LoginDto>



}