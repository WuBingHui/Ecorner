package com.anthony.ecorner.main.login.viewModel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.login.response.LoginDto
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.login.request.LoginBo
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.login.LoginModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi

class LoginViewModel(
    private val loginModel: LoginModel
) : BaseViewModel() {

    enum class Result(val value:Boolean){
        SUCCESS(true),FAILED(false)
    }

    val onLogin: MutableLiveData<Resource<LoginDto>> by lazy { MutableLiveData<Resource<LoginDto>>() }

    fun postLogin(loginBo: LoginBo) {
        loginModel.postLogin(loginBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Result.SUCCESS.value) {
                    onLogin.value = Resource.success(dto)
                } else {
                    onLogin.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onLogin.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}