package com.anthony.ecorner.model.login

import com.anthony.ecorner.service.login.LoginService
import com.csnt.android_sport.extension.ioToUi

class LoginModel(val service: LoginService) {


    fun postLogin(userName:String,password:String) =
        service.postLogin(userName,password).ioToUi()


}