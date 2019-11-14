package com.anthony.ecorner.main.message.view.viewModel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.message.response.MessageDto
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.home.HomeModel
import com.anthony.ecorner.model.message.MessageModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi


class MessageViewModel(
    private val messageModel: MessageModel
) : BaseViewModel() {

    val onNotify: MutableLiveData<Resource<MessageDto>> by lazy { MutableLiveData<Resource<MessageDto>>() }

    fun getNotify() {
        messageModel.getNotify().ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onNotify.value = Resource.success(dto)
                } else {
                    onNotify.value = Resource.error("", dto)
                }
            }, { t: Throwable? -> onNotify.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}