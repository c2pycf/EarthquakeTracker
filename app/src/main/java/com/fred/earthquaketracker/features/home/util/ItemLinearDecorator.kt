package com.fred.earthquaketracker.features.home.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ItemLinearDecorator(@DimenRes val padding: Int) : RecyclerView.ItemDecoration(){

    /**
     * set padding decorator to items in the recycler view
     */

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.run {
            left = padding
            right = padding
            bottom = padding
            top = padding
        }

    }
}