package com.anthony.ecorner.service.personal

import com.anthony.ecorner.dto.personal.response.PersonalDto
import io.reactivex.Single
import retrofit2.http.POST

interface PersonalService {

    /**
     * 登出
     */
    @POST("api/auth/logout")
    fun postLogout(): Single<PersonalDto>



}