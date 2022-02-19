package me.nyaken.gg_score.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import me.nyaken.common.getCurrencyString
import me.nyaken.gg_score.R
import me.nyaken.gg_score.home.adapter.LeagueAdapter
import me.nyaken.network.model.LeagueData
import kotlin.math.ceil

@BindingAdapter("item_profile")
fun ImageView.setProfile(
    url: String?
) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions.bitmapTransform(MultiTransformation(CircleCrop())))
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("item_image_src")
fun ImageView.setImageSrc(
    url: String?
) {
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("item_tier_lp")
fun TextView.setTierLP(
    lp: Int
) {
    text =
        String.format(
            this.context.getString(R.string.text_format_lp),
            lp.getCurrencyString()
        )
}

@BindingAdapter("item_tier_score")
fun TextView.setTierScore(
    item: LeagueData
) {
    val percent = ceil(item.wins / (item.wins + item.losses).toDouble() * 100).toInt()
    text =
        String.format(
            this.context.getString(R.string.text_format_score),
            item.wins.getCurrencyString(),
            item.losses.getCurrencyString(),
            percent.getCurrencyString()
        )
}

@BindingAdapter("item_leagues")
fun RecyclerView.setLeagues(
    item: List<LeagueData>?
) {
    item?.forEach {
        (adapter as? LeagueAdapter)?.addItem(it)
    }
}