package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummonerGenetoryMatchesResponse(
    val games: List<GameData>
): Parcelable