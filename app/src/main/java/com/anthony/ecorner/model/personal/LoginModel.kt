package com.anthony.ecorner.model.personal

import com.anthony.ecorner.dto.personal.resquest.DeleteMyCollectBo
import com.anthony.ecorner.dto.personal.resquest.ProfileBo
import com.anthony.ecorner.dto.personal.resquest.UpdateProfileBo
import com.anthony.ecorner.service.personal.PersonalService
import com.csnt.android_sport.extension.ioToUi

class PersonalModel(val service: PersonalService) {

    fun postLogout() =
        service.postLogout().ioToUi()

    fun postProfile(profileBo: ProfileBo) =
        service.postProfile(profileBo).ioToUi()

    fun postUpdateProfile(updateProfileBo: UpdateProfileBo) =
        service.postUpdateProfile(updateProfileBo).ioToUi()

    fun getMyCollect() =
        service.getMyCollect().ioToUi()

    fun postDeleteMyCollect(deleteMyCollectBo: DeleteMyCollectBo) =
        service.postDeleteMyCollect(deleteMyCollectBo).ioToUi()

}