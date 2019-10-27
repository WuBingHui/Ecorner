package com.anthony.ecorner.koin


import com.anthony.ecorner.model.LoginModel
import org.koin.dsl.module

val repositoryModule = module {

    // Login
    factory { LoginModel(get()) }


}