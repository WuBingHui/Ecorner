package com.anthony.ecorner.main.commodity.ItemDecoration

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


// 自定义条目修饰类
class SpaceItemDecoration(private val space: Int, private val column: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        // 每列分配的间隙大小，包括左间隙和右间隙
        val colPadding = space * (column + 1) / column
        // 列索引
        val colIndex = position % column
        // 列左、右空隙。右间隙=space-左间隙
        outRect.left = space * (colIndex + 1) - colPadding * colIndex
        outRect.right = colPadding * (colIndex + 1) - space * (colIndex + 1)
        // 行间距
        if (position >= column) {
            outRect.top = space
        }
    }
}