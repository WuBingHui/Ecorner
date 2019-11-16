package com.anthony.ecorner.koin


import com.anthony.ecorner.main.registered.viewModel.RegisteredViewModel
import com.anthony.ecorner.model.commodity.CommodityModel
import com.anthony.ecorner.model.home.HomeModel
import com.anthony.ecorner.model.login.LoginModel
import com.anthony.ecorner.model.message.MessageModel
import com.anthony.ecorner.model.my_rent.MyRentModel
import com.anthony.ecorner.model.personal.PersonalModel
import com.anthony.ecorner.model.registered.RegisteredModel
import com.anthony.ecorner.model.upload.UploadModel
import com.anthony.ecorner.service.message.MessageService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {

    // Login
    factory { LoginModel(get()) }
    //Registered
    factory { RegisteredModel(get()) }
    //Personal
    factory { PersonalModel(get()) }
    //home
    factory { HomeModel(get()) }
    //商品列表
    factory { CommodityModel(get()) }
    //訊息列表
    factory { MessageModel(get()) }

    //我的租賃列表
    factory { MyRentModel(get()) }

    //商品上傳
    factory { UploadModel(get()) }
}