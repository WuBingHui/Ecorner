package com.anthony.ecorner.service.personal

import com.anthony.ecorner.dto.personal.response.PersonalDto
import com.anthony.ecorner.dto.personal.response.ProfileDto
import com.anthony.ecorner.dto.personal.response.UpdateProfileDto
import com.anthony.ecorner.dto.personal.resquest.ProfileBo
import com.anthony.ecorner.dto.personal.resquest.UpdateProfileBo
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface PersonalService {

    /**
     * 登出
     */
    @POST("api/auth/logout")
    fun postLogout(): Single<PersonalDto>

    /**
     * 取得個人資料
     */
    @POST("api/auth/profile")
    fun postProfile(@Body profileBo: ProfileBo): Single<ProfileDto>


    /**
     * 個人資料
     */
    @POST("api/auth/update")
    fun postUpdateProfile(@Body updateProfileBo: UpdateProfileBo): Single<UpdateProfileDto>
}