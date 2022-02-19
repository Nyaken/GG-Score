package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummonerMatchesResponse(
    val games: List<GameData>,
    val champions: List<ChampionData>,
    val positions: List<PositionData>,
    val summary: SummaryData
): Parcelable