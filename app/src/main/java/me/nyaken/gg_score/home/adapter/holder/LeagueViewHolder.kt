package me.nyaken.gg_score.home.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.nyaken.gg_score.databinding.ItemLeagueViewHolderBinding
import me.nyaken.gg_score.home.MainViewModel
import me.nyaken.network.model.LeagueData

class LeagueViewHolder(
    private val binding: ItemLeagueViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(viewModel: MainViewModel, item: LeagueData) {
        binding.vm = viewModel
        binding.item = item
    }

    companion object {
        fun create(parent: ViewGroup): LeagueViewHolder {
            val binding = ItemLeagueViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LeagueViewHolder(binding)
        }
    }
}
