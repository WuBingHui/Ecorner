package com.anthony.ecorner.model.commodity

import com.anthony.ecorner.dto.commodity.request.ApplyCommodityBo
import com.anthony.ecorner.service.commodity.CommodityService
import com.csnt.android_sport.extension.ioToUi

class CommodityModel(val service: CommodityService) {


    fun getUniqueCommodity(categoryName:String,page:Int,limit:Int) =
        service.getUniqueCommodity(categoryName,page,limit).ioToUi()

    fun getCommodityDetail(id:Int) =
        service.getCommodityDetail(id).ioToUi()

    fun postApply(applyCommodityBo: ApplyCommodityBo) =
        service.postApply(applyCommodityBo).ioToUi()

}