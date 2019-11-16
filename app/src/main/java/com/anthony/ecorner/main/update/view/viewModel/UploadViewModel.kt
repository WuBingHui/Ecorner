package com.anthony.ecorner.main.update.view.viewModel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.message.response.MessageDto
import com.anthony.ecorner.dto.my_rent.request.ReplyApplicantBo
import com.anthony.ecorner.dto.my_rent.response.MyRentDto
import com.anthony.ecorner.dto.my_rent.response.ReplyApplicationDto
import com.anthony.ecorner.dto.upload.request.UploadBo
import com.anthony.ecorner.dto.upload.response.UploadDto
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.home.HomeModel
import com.anthony.ecorner.model.message.MessageModel
import com.anthony.ecorner.model.my_rent.MyRentModel
import com.anthony.ecorner.model.upload.UploadModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part


class UploadViewModel(
    private val uploadModel: UploadModel
) : BaseViewModel() {

    val onUploaad: MutableLiveData<Resource<UploadDto>> by lazy { MutableLiveData<Resource<UploadDto>>() }


    fun postUploaad(
        map: Map<String, RequestBody> ,  parts: List<MultipartBody.Part>
    ) {
        uploadModel.postUpload(map,parts).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onUploaad.value = Resource.success(dto)
                } else {
                    onUploaad.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onUploaad.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}