package com.anthony.ecorner.service.login

import com.anthony.ecorner.dto.login.request.LoginBo
import com.anthony.ecorner.dto.login.response.LoginDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    /**
     * 登入
     */
    @POST("api/auth/login")
    fun postLogin(@Body loginBo: LoginBo): Single<LoginDto>



}