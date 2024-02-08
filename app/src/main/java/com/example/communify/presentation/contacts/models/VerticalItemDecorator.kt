package com.example.communify.presentation.contacts.models

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecorator(val startMargin: Int, val endMargin: Int, val interitemMargin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = interitemMargin
        val pos = parent.getChildAdapterPosition(view)
        if (pos == 0) {
            outRect.top = startMargin
        }
        else if (pos + 1 == parent.adapter?.itemCount) {
            outRect.bottom = endMargin
        }
    }
}