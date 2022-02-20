package me.nyaken.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameData(
    val mmr: Long,
    val champion: Champion,
    val spells: List<Spell>,
    val items: List<Item>,
    val createDate: Long,
    val gameType: String,
    val gameLength: Long,
    val isWin: Boolean,
    val stats: Stats,
    val peak: List<String>
): Parcelable {

    @Parcelize
    data class Champion(
        val imageUrl: String,
        val level: Int
    ): Parcelable

    @Parcelize
    data class Spell(
        val imageUrl: String
    ): Parcelable

    @Parcelize
    data class Item(
        val imageUrl: String
    ): Parcelable

    @Parcelize
    data class Stats(
        val general: General
    ): Parcelable {

        @Parcelize
        data class General(
            val kill: Int,
            val death: Int,
            val assist: Int,
            val opScoreBadge: String,
            val contributionForKillRate: String,
            val largestMultiKillString: String
        ): Parcelable
    }

}

/**
games → champion : 챔피언정보
games → spells : 챔피언스펠
games → items : 챔피언아이템
games → createDate : 게임시작 시간
games → gameType : 게임타입
games → gameLength : 전체게임시간
games → isWin : 승리/ 패배
games → stats->general → kills : 킬수
games → stats->general → deaths : 데스수
games → stats->general → assists : 어시스트수
games → stats->general → opScoreBadge : MVP,ACE 뱃지
games → stats->general → contributionForKillRate : 킬 관여율
 **/