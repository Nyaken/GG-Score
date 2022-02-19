package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummonerData(
    val name: String,
    val level: Int,
    val profileImageUrl: String,
    val leagues: List<LeagueData>
): Parcelable

/**
summoner → name : 소환사이름
summoner → level : 소환사레벨
summoner → profileImageUrl : 소환사프로필이미지
 **/