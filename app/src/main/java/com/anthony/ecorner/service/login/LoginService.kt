package com.anthony.ecorner.service.login

import com.anthony.ecorner.dto.login.request.LoginBo
import com.anthony.ecorner.dto.login.response.LoginDto
import com.anthony.ecorner.dto.personal.response.ProfileDto
import com.anthony.ecorner.dto.personal.resquest.ProfileBo
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


    /**
     * 取得個人資料
     */
    @POST("api/auth/profile")
    fun postProfile(@Body profileBo: ProfileBo): Single<ProfileDto>
}