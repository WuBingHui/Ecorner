package com.anthony.ecorner.main.base

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anthony.ecorner.R
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {

    protected var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ApiLevel >= 21 才生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //更改StatusBar的顏色
            window.statusBarColor = ContextCompat.getColor(this, R.color.winterNevaLight)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}