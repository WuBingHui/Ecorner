package com.anthony.ecorner.model

import com.anthony.ecorner.service.LoginService
import com.csnt.android_sport.extension.ioToUi

class LoginModel(val service: LoginService) {


    fun postLogin(userName:String,password:String) =
        service.postLogin(userName,password).ioToUi()


}