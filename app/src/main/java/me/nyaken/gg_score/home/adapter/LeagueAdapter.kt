package me.nyaken.gg_score.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.nyaken.gg_score.home.MainViewModel
import me.nyaken.gg_score.home.adapter.holder.LeagueViewHolder
import me.nyaken.network.model.LeagueData

class LeagueAdapter(
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<LeagueViewHolder>() {

    private val items: ArrayList<LeagueData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LeagueViewHolder.create(parent)

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindView(viewModel, items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clearItems() {
        notifyItemRangeRemoved(0, itemCount)
        items.clear()
    }

    fun addItem(item: LeagueData) {
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

}