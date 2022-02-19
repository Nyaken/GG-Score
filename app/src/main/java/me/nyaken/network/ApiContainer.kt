package me.nyaken.network

import io.reactivex.rxjava3.core.Observable
import me.nyaken.network.model.SummonerGenetoryMatchesResponse
import me.nyaken.network.model.SummonerGenetoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiContainer {

    @GET("/summoner/genetory")
    fun summonerGenetory(
    ): Observable<Response<SummonerGenetoryResponse>>

    @GET("/summoner/genetory/matches")
    fun summonerGenetoryMatches(
        @Query("lastMatch") last: String
    ): Observable<Response<SummonerGenetoryMatchesResponse>>

}