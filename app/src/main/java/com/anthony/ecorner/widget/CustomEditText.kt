package com.anthony.ecorner.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.anthony.ecorner.R
import kotlinx.android.synthetic.main.view_custom_edit_text.view.*

class CustomEditText constructor(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {


    private var titleText: String? = ""
    private var iconImage: Drawable? =
        ContextCompat.getDrawable(context, R.mipmap.outline_person_outline_white_18)
    private var hint: String? = "Default"

    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom_edit_text, this)
        initView(context, attrs)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun initView(context: Context, attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditTextView)

        hint = typedArray.getString(R.styleable.CustomEditTextView_hint)

        iconImage = typedArray.getDrawable(R.styleable.CustomEditTextView_icon)

        titleText = typedArray.getString(R.styleable.CustomEditTextView_title)

        title.hint = hint

        icon.background = iconImage

        typedArray.recycle()

        isFocusable = true
        isFocusableInTouchMode = true

    }

    fun setIcon(@NonNull iconImage: Drawable): CustomEditText {
        this.iconImage = iconImage
        return this
    }


    fun setHint(hint: String): CustomEditText {
        this.hint = hint
        return this
    }


    fun setPasswordMode(): CustomEditText {
        title.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        return this
    }

    fun apply() {

        icon.background = iconImage

        title.hint = hint
    }

    fun getText(): String {
        return title.text.toString()
    }

}