@file:JvmName("ViewUtil")

package me.nyaken.common

fun Int?.getCurrencyString() = String.format("%,d", this)
