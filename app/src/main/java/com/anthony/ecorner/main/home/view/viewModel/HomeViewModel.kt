package com.anthony.ecorner.main.home.view.viewModel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.home.reponse.SearchDto
import com.anthony.ecorner.dto.home.request.SearchBo
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.home.HomeModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi

class HomeViewModel(
    private val homeModel: HomeModel
) : BaseViewModel() {

    val onCommodity: MutableLiveData<Resource<CommodityDto>> by lazy { MutableLiveData<Resource<CommodityDto>>() }
    val onSearch: MutableLiveData<Resource<SearchDto>> by lazy { MutableLiveData<Resource<SearchDto>>() }

    fun getCommodity() {
        homeModel.getCommodity().ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onCommodity.value = Resource.success(dto)
                } else {
                    onCommodity.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onCommodity.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun getProductSearch(keyword: String,category:String) {
        homeModel.getProductSearch(keyword,category).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onSearch.value = Resource.success(dto)
                } else {
                    onSearch.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onSearch.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

}