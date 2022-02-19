package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummonerGenetoryResponse(
    val summoner: SummonerData
): Parcelable