package me.nyaken.network

import io.reactivex.rxjava3.core.Observable
import me.nyaken.network.model.SummonerMatchesResponse
import me.nyaken.network.model.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiContainer {

    @GET("/summoner/{user}")
    fun summonerDetail(
        @Path("user") user: String
    ): Observable<Response<SummonerResponse>>

    @GET("/summoner/{user}/matches")
    fun summonerMatches(
        @Path("user") user: String,
        @Query("lastMatch") last: String
    ): Observable<Response<SummonerMatchesResponse>>

}