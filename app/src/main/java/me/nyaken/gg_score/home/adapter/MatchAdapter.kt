package me.nyaken.gg_score.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.nyaken.gg_score.home.MainViewModel
import me.nyaken.gg_score.home.adapter.holder.MatchViewHolder
import me.nyaken.network.model.GameData

class MatchAdapter(
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<MatchViewHolder>() {

    private val items: ArrayList<GameData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MatchViewHolder.create(parent)

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindView(viewModel, items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clearItems() {
        notifyItemRangeRemoved(0, itemCount)
        items.clear()
    }

    fun addItem(item: GameData) {
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

}