package com.anthony.ecorner.service.personal

import com.anthony.ecorner.dto.personal.response.*
import com.anthony.ecorner.dto.personal.resquest.DeleteMyCollectBo
import com.anthony.ecorner.dto.personal.resquest.ProfileBo
import com.anthony.ecorner.dto.personal.resquest.UpdateProfileBo
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
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

    /**
     * 我的收藏
     */
    @GET("api/collet/list")
    fun getMyCollect(): Single<MyCollectDto>

    /**
     * 刪除我的收藏
     */
    @POST("api/collet/remove")
    fun postDeleteMyCollect(@Body deleteMyCollectBo: DeleteMyCollectBo): Single<DeleteMyCollectDto>



}