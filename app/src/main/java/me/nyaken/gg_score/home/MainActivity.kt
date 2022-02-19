package me.nyaken.gg_score.home

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding4.material.offsetChanges
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.nyaken.gg_score.BaseActivity
import me.nyaken.gg_score.R
import me.nyaken.gg_score.databinding.ActivityMainBinding
import me.nyaken.gg_score.home.adapter.LeagueAdapter
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    private val leagueAdapter: LeagueAdapter by lazy {
        LeagueAdapter(viewModel)
    }

    override fun viewBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun setupObserve() {
        viewModel.loading.observe(this, Observer {

        })

        viewModel.summonerData.observe(this, Observer {
            getSummonerMatches()
        })

        binding.appbar.offsetChanges()
            .subscribeBy {
                binding.toolbar.visibility =
                    if (abs(it) - binding.appbar.totalScrollRange == 0) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
            .addToDisposable()

        binding.cardviewRefreshScore.clicks()
            .subscribeBy {
                onRefresh()
            }
            .addToDisposable()
    }

    override fun initLayout() {
        binding.listLeagues.adapter = leagueAdapter
        onRefresh()
    }

    private fun getSummonerDetail() {
        viewModel.summonerDetailData()
    }

    private fun getSummonerMatches() {
        viewModel.summonerMatchesData()
    }

    override fun onRefresh() {
        leagueAdapter.clearItems()
        viewModel.clear()
        getSummonerDetail()
    }

}