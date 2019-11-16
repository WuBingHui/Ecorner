package com.anthony.ecorner.model.upload

import com.anthony.ecorner.dto.my_rent.request.ReplyApplicantBo
import com.anthony.ecorner.dto.upload.request.UploadBo
import com.anthony.ecorner.service.message.MessageService
import com.anthony.ecorner.service.my_rent.MyRentService
import com.anthony.ecorner.service.upload.UploadService
import com.csnt.android_sport.extension.ioToUi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadModel(val service: UploadService) {




    fun postUpload(  map: Map<String, RequestBody> , parts: List<MultipartBody.Part>) =
        service.postUpload(map,parts).ioToUi()
}