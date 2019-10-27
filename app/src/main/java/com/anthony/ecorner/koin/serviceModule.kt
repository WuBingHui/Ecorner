package com.anthony.ecorner.koin



import com.anthony.ecorner.service.LoginService
import org.koin.dsl.module

val serviceModule = module {
    // Login
    factory<LoginService> { createService(get()) }

}