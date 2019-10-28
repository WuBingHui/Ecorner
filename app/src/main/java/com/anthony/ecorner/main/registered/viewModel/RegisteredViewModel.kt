package com.anthony.ecorner.main.registered.viewModel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.registered.request.RegisteredBo
import com.anthony.ecorner.dto.registered.response.RegisteredDto
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.registered.RegisteredModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi

class RegisteredViewModel(
    private val registeredModel: RegisteredModel
) : BaseViewModel() {

    enum class Result(val value:Boolean){
        Success(true),FAILED(false)
    }

    val onRegistered: MutableLiveData<Resource<RegisteredDto>> by lazy { MutableLiveData<Resource<RegisteredDto>>() }

    fun postRegistered(registeredBo: RegisteredBo) {
        registeredModel.postRegistered(registeredBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Result.Success.value) {
                    onRegistered.value = Resource.success(dto)
                } else {
                    onRegistered.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onRegistered.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}