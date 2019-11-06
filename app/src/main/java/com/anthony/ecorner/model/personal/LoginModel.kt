package com.anthony.ecorner.model.personal

import com.anthony.ecorner.service.personal.PersonalService
import com.csnt.android_sport.extension.ioToUi

class PersonalModel(val service: PersonalService) {

    fun postLogout() =
        service.postLogout().ioToUi()


}