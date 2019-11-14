package com.anthony.ecorner.model.message

import com.anthony.ecorner.service.message.MessageService
import com.csnt.android_sport.extension.ioToUi

class MessageModel(val service: MessageService) {


    fun getNotify() =
        service.getNotify().ioToUi()


}