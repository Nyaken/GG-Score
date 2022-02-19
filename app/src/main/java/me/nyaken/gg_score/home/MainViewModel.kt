package me.nyaken.gg_score.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import me.nyaken.domain.usecase.SummonerDetailUseCase
import me.nyaken.domain.usecase.SummonerMatchesUseCase
import me.nyaken.gg_score.BaseViewModel
import me.nyaken.network.model.*
import me.nyaken.utils.Event
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val summonerDetailUseCase: SummonerDetailUseCase,
    private val summonerMatchesUseCase: SummonerMatchesUseCase
): BaseViewModel() {

    private val user: String = "genetory"
    private var lastMatch: Long? = null

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun loading(item: Boolean) {
        _loading.value = item
    }

    fun clear() {
        lastMatch = null
    }

    private val _summonerData = MutableLiveData<SummonerData>()
    val summonerData: LiveData<SummonerData>
        get() = _summonerData

    fun summonerData(item: SummonerData) {
        _summonerData.value = item
    }

    private val _summonerSummaryData = MutableLiveData<SummonerMatchesResponse>()
    val summonerSummaryData: LiveData<SummonerMatchesResponse>
        get() = _summonerSummaryData

    private fun summonerSummaryData(item: SummonerMatchesResponse) {
        _summonerSummaryData.value = item
    }

    private val _matchesData = MutableLiveData<Event<List<GameData>>>()
    val matchesData: LiveData<Event<List<GameData>>>
        get() = _matchesData
    
    fun matchesData(item: List<GameData>) {
        _matchesData.value = Event(item)
    }

    fun summonerDetailData() =
        summonerDetailUseCase(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                loading(true)
            }
            .doOnTerminate{
                loading(false)
            }
            .subscribeBy(
                onNext = {
                    if(it.isSuccessful) {
                        it.body()?.let {
                            summonerData(it.summoner)
                        }
                    } else throw NullPointerException()
                }, onError = {
                    it.printStackTrace()
                }
            )
            .addToDisposable()

    fun summonerMatchesData() =
        summonerMatchesUseCase(user, lastMatch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                loading(true)
            }
            .doOnTerminate{
                loading(false)
            }
            .subscribeBy(
                onNext = {
                    if(it.isSuccessful) {
                        it.body()?.let { response ->
                            if(lastMatch == null) {
                                summonerSummaryData(
                                    SummonerMatchesResponse(
                                        games = emptyList(),
                                        champions = response.champions,
                                        summary = response.summary,
                                        positions = response.positions
                                    )
                                )
                            }
                            matchesData(response.games)
                            lastMatch = response.games.last().createDate
                        }
                    } else throw NullPointerException()
                }, onError = {
                    it.printStackTrace()
                }
            )
            .addToDisposable()
}