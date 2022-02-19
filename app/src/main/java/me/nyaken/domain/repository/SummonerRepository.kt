package me.nyaken.domain.repository

import io.reactivex.rxjava3.core.Observable
import me.nyaken.network.ApiContainer
import me.nyaken.network.model.SummonerMatchesResponse
import me.nyaken.network.model.SummonerResponse
import retrofit2.Response

class SummonerRepository(
    private val api: ApiContainer
) {

    fun summonerDetail(
        user: String
    ): Observable<Response<SummonerResponse>> =
        api.summonerDetail(user)

    fun summonerMatches(
        user: String,
        lastMatch: Long?
    ): Observable<Response<SummonerMatchesResponse>> =
        api.summonerMatches(user, lastMatch)

}