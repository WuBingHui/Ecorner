package com.anthony.ecorner.extension

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics


fun Context.dp2px(dp :Int):Int{
    return  (dp * resources.displayMetrics.density).toInt()
}

