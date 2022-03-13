package com.gritbus.hipchon.utils

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R

class ItemDecorationWithStroke() : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingStart + dpToPx(parent.context, 20)
        val right = parent.width - parent.paddingEnd - dpToPx(parent.context, 20)

        val paint = Paint().apply {
            color = ContextCompat.getColor(parent.context, R.color.gray02)
        }

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + dpToPx(parent.context, 1)

            c.drawRect(left.toFloat(), top, right.toFloat(), bottom, paint)
        }
    }
}
