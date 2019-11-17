package com.anthony.ecorner.service.my_rent

import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.message.response.MessageDto
import com.anthony.ecorner.dto.my_rent.request.ReplyApplicantBo
import com.anthony.ecorner.dto.my_rent.response.MyRentDto
import com.anthony.ecorner.dto.my_rent.response.MyUploadDto
import com.anthony.ecorner.dto.my_rent.response.ReplyApplicationDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyRentService{


    /**
     * 我的租賃
     */
    @GET("api/order/list/leases")
    fun getMyRent(): Single<MyRentDto>


    /**
     * 租賃請求
     */
    @GET("api/order/list/leases-request")
    fun getRentApply(): Single<MyRentDto>

    /**
     *我的上傳
     */
    @GET(" api/product/own")
    fun getMyUpload(): Single<MyUploadDto>

    /**
     *回覆 租賃請求
     */
    @POST("api/order/reply")
    fun postReplyApplicant(@Body replyApplicantBo : ReplyApplicantBo): Single<ReplyApplicationDto>

}