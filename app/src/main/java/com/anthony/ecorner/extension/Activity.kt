package com.anthony.ecorner.extension

import android.app.Activity
import android.util.DisplayMetrics



fun Activity.dp2px(dp :Int):Int{
    return (dp * resources.displayMetrics.density).toInt()
}

fun Activity.getWindowWidth():Int{
    val metric = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metric)
    return metric.widthPixels
}