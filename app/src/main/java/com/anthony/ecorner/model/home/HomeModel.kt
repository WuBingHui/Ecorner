package com.anthony.ecorner.model.home

import com.anthony.ecorner.dto.home.request.SearchBo
import com.anthony.ecorner.dto.login.request.LoginBo
import com.anthony.ecorner.service.home.HomeService
import com.anthony.ecorner.service.login.LoginService
import com.csnt.android_sport.extension.ioToUi
import retrofit2.http.Path

class HomeModel(val service: HomeService) {


    fun getCommodity() =
        service.getCommodity().ioToUi()

    fun getProductSearch( keyword: String,category:String) =
        service.getProductSearch(keyword,category).ioToUi()


}