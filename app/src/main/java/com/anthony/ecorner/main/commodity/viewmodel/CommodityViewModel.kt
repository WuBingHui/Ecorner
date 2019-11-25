package com.anthony.ecorner.main.commodity.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anthony.ecorner.dto.Resource
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.commodity.request.ApplyCommodityBo
import com.anthony.ecorner.dto.commodity.request.CollectBo
import com.anthony.ecorner.dto.commodity.response.ApplyCommodityDto
import com.anthony.ecorner.dto.commodity.response.CollectDto
import com.anthony.ecorner.dto.commodity.response.CommodityDetailDto
import com.anthony.ecorner.dto.commodity.response.UniqueCommodityDto
import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.main.base.BaseViewModel
import com.anthony.ecorner.model.commodity.CommodityModel
import com.anthony.ecorner.model.home.HomeModel
import com.csnt.android_sport.extension.addTo
import com.csnt.android_sport.extension.ioToUi


class CommodityViewModel(
    private val homeModel: HomeModel,
    private val commodityModel: CommodityModel
) : BaseViewModel() {

    val onCommodity: MutableLiveData<Resource<CommodityDto>> by lazy { MutableLiveData<Resource<CommodityDto>>() }

    val onUniqueCommodity: MutableLiveData<Resource<UniqueCommodityDto>> by lazy { MutableLiveData<Resource<UniqueCommodityDto>>() }

    val onCommodityDetail: MutableLiveData<Resource<CommodityDetailDto>> by lazy { MutableLiveData<Resource<CommodityDetailDto>>() }

    val onApplyCommodity: MutableLiveData<Resource<ApplyCommodityDto>> by lazy { MutableLiveData<Resource<ApplyCommodityDto>>() }

    val onCollect: MutableLiveData<Resource<CollectDto>> by lazy { MutableLiveData<Resource<CollectDto>>() }

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

    fun getUniqueCommodity(categoryName:String,page:Int,limit:Int) {
        commodityModel.getUniqueCommodity(categoryName,page,limit).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onUniqueCommodity.value = Resource.success(dto)
                } else {
                    onUniqueCommodity.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onUniqueCommodity.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun getCommodityDetail(id:Int) {
        commodityModel.getCommodityDetail(id).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onCommodityDetail.value = Resource.success(dto)
                } else {
                    onCommodityDetail.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onCommodityDetail.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun postApply(applyCommodityBo: ApplyCommodityBo) {
        commodityModel.postApply(applyCommodityBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onApplyCommodity.value = Resource.success(dto)
                } else {
                    onApplyCommodity.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onApplyCommodity.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }

    fun postCollect(collectBo: CollectBo) {
        commodityModel.postCollect(collectBo).ioToUi().subscribe(
            { dto ->
                if (dto.result == Status.SUCCESS.value) {
                    onCollect.value = Resource.success(dto)
                } else {
                    onCollect.value = Resource.error(dto.error, dto)
                }
            }, { t: Throwable? -> onCollect.value = Resource.error(t?.message, null) }
        ).addTo(compositeDisposable)
    }
}