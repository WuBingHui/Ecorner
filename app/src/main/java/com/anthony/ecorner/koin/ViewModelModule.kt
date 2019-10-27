package com.anthony.ecorner.koin



import com.anthony.ecorner.main.login.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    //Login
    viewModel { LoginViewModel(get()) }



}