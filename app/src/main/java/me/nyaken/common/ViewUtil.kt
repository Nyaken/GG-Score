@file:JvmName("ViewUtil")

package me.nyaken.common

import android.content.Context

fun Int?.getCurrencyString() = String.format("%,d", this)

fun Context.convertDP2Pixel(dp: Float) =
    (dp * (resources.displayMetrics.densityDpi / 160f)).toInt()
