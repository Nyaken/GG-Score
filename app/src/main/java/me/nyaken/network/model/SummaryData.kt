package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummaryData(
    val wins: Int,
    val losses: Int,
    val kills: Int,
    val deaths: Int,
    val assists: Int
): Parcelable