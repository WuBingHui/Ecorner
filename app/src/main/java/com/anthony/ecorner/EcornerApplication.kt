package com.anthony.ecorner


import android.content.Context
import androidx.multidex.MultiDexApplication
import com.anthony.ecorner.koin.ecornerModule
import com.anthony.ecorner.koin.repositoryModule
import com.anthony.ecorner.koin.serviceModule
import com.anthony.ecorner.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class EcornerApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        context = this@EcornerApplication

        startKoin {
            androidContext(this@EcornerApplication)
            modules(listOf(viewModelModule,ecornerModule, serviceModule,repositoryModule))
        }


    }

    companion object {
        private var context: Context? = null
        fun getContext(): Context? = context
    }




}



