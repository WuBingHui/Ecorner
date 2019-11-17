package com.anthony.ecorner.model.my_rent

import com.anthony.ecorner.dto.my_rent.request.DeleteBo
import com.anthony.ecorner.dto.my_rent.request.ReplyApplicantBo
import com.anthony.ecorner.service.message.MessageService
import com.anthony.ecorner.service.my_rent.MyRentService
import com.csnt.android_sport.extension.ioToUi

class MyRentModel(val service: MyRentService) {


    fun getMyRent() =
        service.getMyRent().ioToUi()

    fun getRentApply() =
        service.getRentApply().ioToUi()

    fun getMyUpload() =
            service.getMyUpload().ioToUi()

    fun postReplyApplicant(replyApplicantBo: ReplyApplicantBo) =
        service.postReplyApplicant(replyApplicantBo).ioToUi()

    fun postDelete(deleteBo : DeleteBo) =
            service.postDelete(deleteBo).ioToUi()


}