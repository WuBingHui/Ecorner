package com.anthony.ecorner.koin



import com.anthony.ecorner.service.commodity.CommodityService
import com.anthony.ecorner.service.home.HomeService
import com.anthony.ecorner.service.login.LoginService
import com.anthony.ecorner.service.message.MessageService
import com.anthony.ecorner.service.my_rent.MyRentService
import com.anthony.ecorner.service.personal.PersonalService
import com.anthony.ecorner.service.registered.RegisteredService
import com.anthony.ecorner.service.upload.UploadService
import org.koin.dsl.module

val serviceModule = module {
    // Login
    factory<LoginService> { createService(get()) }
    //Registered
    factory<RegisteredService> { createService(get()) }

    //Personal
    factory<PersonalService> { createService(get()) }

    //Personal
    factory<HomeService> { createService(get()) }

    //商品列表
    factory<CommodityService> { createService(get()) }

    //訊息列表
    factory<MessageService> { createService(get()) }

    //我的租賃列表
    factory<MyRentService> { createService(get()) }

    //商品上傳
    factory<UploadService> { createService(get()) }
}