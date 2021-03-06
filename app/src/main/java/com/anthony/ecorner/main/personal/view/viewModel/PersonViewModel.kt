package com.anthony.ecorner.main.personal.view.viewModel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.login.response.LoginDto
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.personal.response.*
import com.anthony.ecorner.dto.personal.resquest.DeleteMyCollectBo
import com.anthony.ecorner.dto.personal.resquest.ProfileBo
import com.anthony.ecorner.dto.personal.resquest.UpdateProfileBo
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.personal.PersonalModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi

class PersonalViewModel(
    private val personalModel: PersonalModel
) : BaseViewModel() {

    val onLogout: MutableLiveData<Resource<PersonalDto>> by lazy { MutableLiveData<Resource<PersonalDto>>() }
    val onProfile: MutableLiveData<Resource<ProfileDto>> by lazy { MutableLiveData<Resource<ProfileDto>>() }
    val onUpdateProfile: MutableLiveData<Resource<UpdateProfileDto>> by lazy { MutableLiveData<Resource<UpdateProfileDto>>() }
    val onMyCollect: MutableLiveData<Resource<MyCollectDto>> by lazy { MutableLiveData<Resource<MyCollectDto>>() }
    val onDeleteMyCollect: MutableLiveData<Resource<DeleteMyCollectDto>> by lazy { MutableLiveData<Resource<DeleteMyCollectDto>>() }

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

    fun postProfile(profileBo:ProfileBo) {
        personalModel.postProfile(profileBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onProfile.value = Resource.success(dto)
                } else {
                    onProfile.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onProfile.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun postUpdateProfile(updateProfileBo: UpdateProfileBo) {
        personalModel.postUpdateProfile(updateProfileBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onUpdateProfile.value = Resource.success(dto)
                } else {
                    onUpdateProfile.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onUpdateProfile.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun getMyCollect() {
        personalModel.getMyCollect().ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onMyCollect.value = Resource.success(dto)
                } else {
                    onMyCollect.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onMyCollect.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun postDeleteMyCollect(deleteMyCollectBo: DeleteMyCollectBo) {
        personalModel.postDeleteMyCollect(deleteMyCollectBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onDeleteMyCollect.value = Resource.success(dto)
                } else {
                    onDeleteMyCollect.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onDeleteMyCollect.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}