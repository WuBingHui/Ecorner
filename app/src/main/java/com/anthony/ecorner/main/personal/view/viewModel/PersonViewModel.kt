package com.anthony.ecorner.main.personal.view.viewModel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.login.response.LoginDto
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.personal.response.PersonalDto
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.personal.PersonalModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi

class PersonalViewModel(
    private val personalModel: PersonalModel
) : BaseViewModel() {

    val onLogout: MutableLiveData<Resource<PersonalDto>> by lazy { MutableLiveData<Resource<PersonalDto>>() }

    fun postLogout() {
        personalModel.postLogout().ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onLogout.value = Resource.success(dto)
                } else {
                    onLogout.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onLogout.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}