package com.anthony.ecorner.service.home

import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.login.request.LoginBo
import com.anthony.ecorner.dto.login.response.LoginDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeService {

    /**
     * 取得首頁商品
     */
    @GET("api/auth/login")
    fun getCommodity(): Single<CommodityDto>



}