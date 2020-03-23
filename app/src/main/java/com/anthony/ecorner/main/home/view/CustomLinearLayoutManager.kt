package com.anthony.ecorner.main.home.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomLinearLayoutManager(context: Context) : LinearLayoutManager(context){

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return 300
    }

}