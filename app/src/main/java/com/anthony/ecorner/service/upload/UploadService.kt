package com.anthony.ecorner.service.upload

import com.anthony.ecorner.dto.upload.request.UploadBo
import com.anthony.ecorner.dto.upload.response.UploadDto
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UploadService {


    /**
     *上傳商品
     */
    @Multipart
    @POST("api/product/store")
    fun postUpload(
        @PartMap map: Map<String,@JvmSuppressWildcards RequestBody>, @Part parts: List<MultipartBody.Part>
    ):  Single<UploadDto>

}