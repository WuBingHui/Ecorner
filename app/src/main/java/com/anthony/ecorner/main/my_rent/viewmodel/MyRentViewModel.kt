package com.anthony.ecorner.main.my_rent.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.message.response.MessageDto
import com.anthony.ecorner.dto.my_rent.request.DeleteBo
import com.anthony.ecorner.dto.my_rent.request.ReplyApplicantBo
import com.anthony.ecorner.dto.my_rent.response.DeleteDto
import com.anthony.ecorner.dto.my_rent.response.MyRentDto
import com.anthony.ecorner.dto.my_rent.response.MyUploadDto
import com.anthony.ecorner.dto.my_rent.response.ReplyApplicationDto
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.home.HomeModel
import com.anthony.ecorner.model.message.MessageModel
import com.anthony.ecorner.model.my_rent.MyRentModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi


class MyRentViewModel(
    private val myRentModel: MyRentModel
) : BaseViewModel() {

    val onMyRent: MutableLiveData<Resource<MyRentDto>> by lazy { MutableLiveData<Resource<MyRentDto>>() }

    val onRentApply: MutableLiveData<Resource<MyRentDto>> by lazy { MutableLiveData<Resource<MyRentDto>>() }

    val onMyUpload: MutableLiveData<Resource<MyUploadDto>> by lazy { MutableLiveData<Resource<MyUploadDto>>() }

    val onDelete: MutableLiveData<Resource<DeleteDto>> by lazy { MutableLiveData<Resource<DeleteDto>>() }

    val onReplyApplicant: MutableLiveData<Resource<ReplyApplicationDto>> by lazy { MutableLiveData<Resource<ReplyApplicationDto>>() }

    fun getMyRent() {
        myRentModel.getMyRent().ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onMyRent.value = Resource.success(dto)
                } else {
                    onMyRent.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onMyRent.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun getRentApply() {
        myRentModel.getRentApply().ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onRentApply.value = Resource.success(dto)
                } else {
                    onRentApply.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onRentApply.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun getMyUpload() {
        myRentModel.getMyUpload().ioToUi().subscribe(
                { dto ->
                    if (dto.result == Status.SUCCESS.value) {
                        onMyUpload.value = Resource.success(dto)
                    } else {
                        onMyUpload.value = Resource.error(dto.error, dto)
                    }
                }, { t: Throwable? -> onMyUpload.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun postReplyApplicant(replyApplicantBo: ReplyApplicantBo) {
        myRentModel.postReplyApplicant(replyApplicantBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onReplyApplicant.value = Resource.success(dto)
                } else {
                    onReplyApplicant.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onReplyApplicant.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun postDelete(deleteBo : DeleteBo) {
        myRentModel.postDelete(deleteBo).ioToUi().subscribe(
                { dto ->
                    if (dto.result == Status.SUCCESS.value) {
                        onDelete.value = Resource.success(dto)
                    } else {
                        onDelete.value = Resource.error(dto.error, dto)
                    }
                }, { t: Throwable? -> onDelete.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}