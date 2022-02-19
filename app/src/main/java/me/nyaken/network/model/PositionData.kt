package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PositionData(
    val games: Int,
    val position: String
): Parcelable