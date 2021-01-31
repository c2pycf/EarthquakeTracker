package com.fred.earthquaketracker.common.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

/**
 * Color extension function to resolve color attributes based on the theme
 */

@ColorInt
fun Context.resolveColorReference(@AttrRes colorAttrRes: Int): Int =
    TypedValue().let { typedValue ->
        theme.resolveAttribute(colorAttrRes, typedValue, true)
        val color = typedValue.run { if (resourceId != 0) resourceId else data }
        return@let ContextCompat.getColor(this, color)
    }