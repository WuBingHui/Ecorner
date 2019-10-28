package com.anthony.ecorner.koin


import com.anthony.ecorner.main.registered.viewModel.RegisteredViewModel
import com.anthony.ecorner.model.login.LoginModel
import com.anthony.ecorner.model.registered.RegisteredModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {

    // Login
    factory { LoginModel(get()) }
    //Registered
    factory { RegisteredModel(get()) }

}