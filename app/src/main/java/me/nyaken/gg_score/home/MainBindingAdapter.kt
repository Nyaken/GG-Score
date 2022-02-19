package me.nyaken.gg_score.home

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
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
import me.nyaken.network.model.ChampionData
import me.nyaken.network.model.LeagueData
import me.nyaken.network.model.PositionData
import me.nyaken.network.model.SummaryData
import kotlin.math.ceil

@BindingAdapter("item_circle_image_src")
fun ImageView.setCircleImageSrc(
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

@BindingAdapter("item_summary_score")
fun TextView.setSummaryScore(
    item: SummaryData?
) {
    item?.let {
        text =
            String.format(
                this.context.getString(R.string.text_format_score_summary),
                item.wins.getCurrencyString(),
                item.losses.getCurrencyString()
            )
    }
}

@BindingAdapter("item_summary_score_kda")
fun TextView.setSummaryScoreKDA(
    item: SummaryData?
) {
    item?.let {
        val htmlText = String.format(
            this.context.getString(R.string.text_format_score_kda),
            item.kills.getCurrencyString(),
            item.assists.getCurrencyString(),
            item.deaths.getCurrencyString()
        )

        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlText)
        }
    }
}

@BindingAdapter("item_kda")
fun TextView.setKDA(
    item: SummaryData?
) {
    item?.let {
        val kda = String.format("%.2f", ((item.kills + item.assists).toDouble() / item.deaths))
        val percent = ceil((item.kills + item.assists) / ((item.kills + item.assists) + item.losses).toDouble() * 100).toInt()
        val htmlText = String.format(
            this.context.getString(R.string.text_format_kda),
            kda,
            percent
        )

        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlText)
        }
    }
}

@BindingAdapter("item_position_image")
fun ImageView.setImageSrc(
    item: List<PositionData>?
) {
    item?.let {
        Glide.with(this.context)
            .load(
                AppCompatResources.getDrawable(
                    this.context,
                    when(it[0].position) {
                        "ADC" -> R.drawable.ic_icon_lol_bot
                        "TOP" -> R.drawable.ic_icon_lol_top
                        "MID" -> R.drawable.ic_icon_lol_mid
                        "SUP" -> R.drawable.ic_icon_lol_sup
                        else -> R.drawable.ic_icon_lol_jng
                    }
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }
}

@BindingAdapter("item_position_percent")
fun TextView.setPositionPercent(
    item: List<PositionData>?
) {
    item?.let {
        var totalGames = 0
        it.forEach {
            totalGames += it.games
        }
        val percent = ceil(it[0].games / totalGames.toDouble() * 100).toInt()
        text = String.format("%s%%", percent.getCurrencyString())
    }
}

@BindingAdapter("item_champion_image", "item_champion_position")
fun ImageView.setChampionImage(
    item: List<ChampionData>?,
    position: Int,
) {
    item?.let {
        if(item.size == 1 && position == 1) {
            this.visibility = View.GONE
            return
        } else {
            this.visibility = View.VISIBLE
        }
        var imageUrl = it[position].imageUrl
        if(imageUrl.startsWith("//")){
            imageUrl = "https:$imageUrl"
        }
        Glide.with(this.context)
            .load(imageUrl)
            .apply(RequestOptions.bitmapTransform(MultiTransformation(CircleCrop())))
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }
}

@BindingAdapter("item_champion_percent", "item_champion_position")
fun TextView.setChampionPercent(
    item: List<ChampionData>?,
    position: Int,
) {
    item?.let {
        if(item.size == 1 && position == 1) {
            this.visibility = View.GONE
            return
        } else {
            this.visibility = View.VISIBLE
        }
        var totalGames = 0
        it.forEach {
            totalGames += it.games
        }
        val percent = ceil(it[position].wins / totalGames.toDouble() * 100).toInt()
        text = String.format("%s%%", percent.getCurrencyString())

        setTextColor(
            ContextCompat.getColor(
                this.context,
                if(percent == 100) R.color.darkish_pink
                else R.color.dark_grey
            )
        )
    }
}