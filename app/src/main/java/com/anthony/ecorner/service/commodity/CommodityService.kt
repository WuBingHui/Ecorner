package com.anthony.ecorner.service.commodity

import com.anthony.ecorner.dto.commodity.request.ApplyCommodityBo
import com.anthony.ecorner.dto.commodity.response.ApplyCommodityDto
import com.anthony.ecorner.dto.commodity.response.CommodityDetailDto
import com.anthony.ecorner.dto.commodity.response.UniqueCommodityDto
import com.anthony.ecorner.main.commodity.view.CommodityActivity
import io.reactivex.Single
import retrofit2.http.*

interface CommodityService {

    /**
     * 取得商品分類頁
     */
    @GET("api/product/category/{categoryName}")
    fun getUniqueCommodity(@Path("categoryName") categoryName:String, @Query("page") page:Int, @Query("limit") limit:Int ): Single<UniqueCommodityDto>


    /**
     * 取得商品詳細
     */
    @GET("api/product/info/{id}")
    fun getCommodityDetail( @Path("id") id:Int ): Single<CommodityDetailDto>

    /**
     * 申請商品租借
     */
    @POST("api/order/create")
    fun postApply(@Body applyCommodityBo: ApplyCommodityBo): Single<ApplyCommodityDto>

}