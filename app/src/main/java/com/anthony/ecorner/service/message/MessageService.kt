package com.anthony.ecorner.service.message

import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.message.response.MessageDto
import io.reactivex.Single
import retrofit2.http.GET

interface MessageService{


    /**
     * 取得通知訊息
     */
    @GET("api/news/list")
    fun getNotify(): Single<MessageDto>


}