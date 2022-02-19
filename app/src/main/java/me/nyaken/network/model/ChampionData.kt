package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChampionData(
    val imageUrl: String,
    val wins: Int
): Parcelable