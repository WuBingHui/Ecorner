package com.anthony.ecorner.service.home

import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.home.reponse.SearchDto
import com.anthony.ecorner.dto.home.request.SearchBo
import com.anthony.ecorner.dto.login.request.LoginBo
import com.anthony.ecorner.dto.login.response.LoginDto
import io.reactivex.Single
import retrofit2.http.*

interface HomeService {

    /**
     * 取得首頁商品
     */
    @GET("api/product/index")
    fun getCommodity(): Single<CommodityDto>

    /**
     * 取得搜尋商品
     */
    @GET("api/product/search")
    fun getProductSearch(@Query("keyword") keyword: String, @Query("category") category:String): Single<SearchDto>

}