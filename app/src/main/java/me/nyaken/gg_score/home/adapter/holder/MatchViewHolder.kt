package me.nyaken.gg_score.home.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.nyaken.gg_score.databinding.ItemMatchViewHolderBinding
import me.nyaken.gg_score.home.MainViewModel
import me.nyaken.network.model.GameData

class MatchViewHolder(
    private val binding: ItemMatchViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(viewModel: MainViewModel, item: GameData) {
        binding.vm = viewModel
        binding.item = item
    }

    companion object {
        fun create(parent: ViewGroup): MatchViewHolder {
            val binding = ItemMatchViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MatchViewHolder(binding)
        }
    }
}
