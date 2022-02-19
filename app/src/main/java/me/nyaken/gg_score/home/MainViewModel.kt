package me.nyaken.gg_score.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import me.nyaken.domain.usecase.SummonerDetailUseCase
import me.nyaken.domain.usecase.SummonerMatchesUseCase
import me.nyaken.gg_score.BaseViewModel
import me.nyaken.network.model.SummonerData
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
                        it.body()?.let {
                            lastMatch = it.games.last().createDate
                        }
                    } else throw NullPointerException()
                }, onError = {

                }
            )
            .addToDisposable()
}