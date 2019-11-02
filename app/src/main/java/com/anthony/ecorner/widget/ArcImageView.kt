package com.anthony.ecorner.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.widget.ImageView
import android.util.AttributeSet


class ArcImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    /*
     *弧形高度
     */
    private val mArcHeight: Int = 100

    override fun onDraw(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(0f, height.toFloat())
        path.quadTo(
            (width / 2).toFloat(),
            (height - 2 * mArcHeight).toFloat(),
            width.toFloat(),
            height.toFloat()
        )
        path.lineTo(width.toFloat(), 0f)
        path.close()
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

    companion object {
        private val TAG = "ArcImageView"
    }

}
