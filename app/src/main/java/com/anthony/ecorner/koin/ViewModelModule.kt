package com.anthony.ecorner.koin



import com.anthony.ecorner.main.commodity.viewmodel.CommodityViewModel
import com.anthony.ecorner.main.home.view.viewModel.HomeViewModel
import com.anthony.ecorner.main.login.viewModel.LoginViewModel
import com.anthony.ecorner.main.message.view.viewModel.MessageViewModel
import com.anthony.ecorner.main.personal.view.viewModel.PersonalViewModel
import com.anthony.ecorner.main.registered.viewModel.RegisteredViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    //Login
    viewModel { LoginViewModel(get()) }
    //Registered
    viewModel { RegisteredViewModel(get()) }
    //Personal
    viewModel { PersonalViewModel(get()) }

    //home
    viewModel { HomeViewModel(get()) }

    //商品
    viewModel { CommodityViewModel(get(),get()) }

    //訊息
    viewModel { MessageViewModel(get()) }
}