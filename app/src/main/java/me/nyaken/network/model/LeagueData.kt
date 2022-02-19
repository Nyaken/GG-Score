package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeagueData(
    val wins: Int,
    val losses: Int,
    val tierRank: TierRank
): Parcelable {

    @Parcelize
    data class TierRank(
        val name: String,
        val tier: String,
        val lp: Int,
        val imageUrl: String
    ): Parcelable

}

/**
summoner → leagues → wins : 이긴 게임수
summoner → leagues → losses : 진게임수
summoner → leagues → tierRank → name : 게임타입
summoner → leagues → tierRank → imageUrl : 티어이미지
 **/