package com.anthony.ecorner.extension

import android.content.Context
import android.text.Editable
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.regex.Pattern

//正則: 字母.數字 為必須
fun String.isLetterAndDigitFormat(): Boolean {
    return Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$").matcher(this).matches()
}

fun String.isEmailFormat(): Boolean {
    return Pattern.compile("^([A-Za-z0-9_\\-.])+@([A-Za-z0-9_\\-.])+\\.([A-Za-z]{2,4})$").matcher(this).matches()
}

/**
 * //【全為英文】返回true  否則false
 */
fun String.isEnglish(): Boolean {
    return this.matches("[a-zA-Z]+".toRegex())
}

/**
 * //【全為中文不分繁體或簡體】返回true  否則false
 */
fun String.isChinese(): Boolean {
    return this.matches("[\\u4E00-\\u9FA5]+".toRegex())
}

/**
 * 是否為台灣手機
 */
fun String.isTaiwanPhone():Boolean{
    return  Pattern.compile("(09)+[0-9]{8}").matcher(this).matches()
}




