package com.anthony.ecorner.model.registered

import com.anthony.ecorner.dto.registered.request.RegisteredBo
import com.anthony.ecorner.service.registered.RegisteredService
import com.csnt.android_sport.extension.ioToUi

class RegisteredModel(val service: RegisteredService) {


    fun postRegistered(registeredBo: RegisteredBo) =
        service.postRegistered(registeredBo).ioToUi()


}