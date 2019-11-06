package com.anthony.ecorner.koin



import com.anthony.ecorner.service.home.HomeService
import com.anthony.ecorner.service.login.LoginService
import com.anthony.ecorner.service.personal.PersonalService
import com.anthony.ecorner.service.registered.RegisteredService
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
}