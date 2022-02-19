package me.nyaken.gg_score.home

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import me.nyaken.gg_score.BaseActivity
import me.nyaken.gg_score.R
import me.nyaken.gg_score.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

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

    }

    override fun initLayout() {
        getSummonerDetail()
    }

    private fun getSummonerDetail() {
        viewModel.summonerDetailData()
    }

    private fun getSummonerMatches() {
        viewModel.summonerMatchesData()
    }
}