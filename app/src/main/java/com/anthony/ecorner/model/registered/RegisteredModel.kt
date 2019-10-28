package com.anthony.ecorner.model.registered

import com.anthony.ecorner.service.registered.RegisteredService
import com.csnt.android_sport.extension.ioToUi

class RegisteredModel(val service: RegisteredService) {


    fun postRegistered(userName:String,password:String,name:String,phone:String,address:String) =
        service.postRegistered(userName,password,name,phone,address).ioToUi()


}