package me.nyaken.gg_score.home

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import me.nyaken.common.convertDP2Pixel
import me.nyaken.common.getCurrencyString
import me.nyaken.gg_score.R
import me.nyaken.gg_score.databinding.RowGameItemBinding
import me.nyaken.gg_score.home.adapter.LeagueAdapter
import me.nyaken.network.model.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
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
            it.kills.getCurrencyString(),
            it.assists.getCurrencyString(),
            it.deaths.getCurrencyString()
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

@BindingAdapter("item_match_is_win_background")
fun LinearLayout.setMatchIsWin(
    is_win: Boolean
) {
    setBackgroundColor(
        ContextCompat.getColor(
            this.context,
            if(is_win) R.color.soft_blue
            else R.color.darkish_pink
        )
    )
}

@BindingAdapter("item_match_is_win")
fun TextView.setMatchIsWin(
    is_win: Boolean
) {
    text =
        this.context.getString(
            if(is_win) R.string.match_is_win else R.string.match_is_loss
        )
}

@BindingAdapter("item_match_score_badge")
fun TextView.setMatchScoreBadge(
    score_badge: String
) {
    this.visibility =
        if(score_badge.isNotEmpty()) View.VISIBLE
        else View.GONE

    text = score_badge
    setBackgroundResource(
        when(score_badge) {
            "ACE" -> R.drawable.shape_badge_ace
            else -> R.drawable.shape_badge_mvp
        }
    )
}

@BindingAdapter("item_match_mmr")
fun TextView.setMatchMMR(
    item_mmr: Long
) {
    val mmr = item_mmr * 1000
    text =
        String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(mmr),
            TimeUnit.MILLISECONDS.toSeconds(mmr) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mmr))
        )
}

@BindingAdapter("item_match_spell_image", "item_match_spell_position")
fun ImageView.setMatchSpellImage(
    item: List<GameData.Spell>?,
    position: Int,
) {
    item?.let {
        Glide.with(this.context)
            .load(it[position].imageUrl)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(context.convertDP2Pixel(4f))
                    )
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }
}

@BindingAdapter("item_match_peak_image", "item_match_peak_position")
fun ImageView.setMatchPeakImage(
    item: List<String>?,
    position: Int,
) {
    item?.let {
        Glide.with(this.context)
            .load(it[position])
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }
}

@BindingAdapter("item_match_kda")
fun TextView.setMatchKDA(
    item: GameData.Stats.General
) {
    val htmlText = String.format(
        this.context.getString(R.string.text_format_score_kda),
        item.kill.getCurrencyString(),
        item.assist.getCurrencyString(),
        item.death.getCurrencyString()
    )

    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(htmlText)
    }
}

@BindingAdapter("item_match_kill_involvement")
fun TextView.setMatchKillInvolvement(
    item: GameData.Stats.General
) {
    val percent = ceil((item.kill + item.assist) / (item.kill + item.assist + item.death).toDouble() * 100).toInt()
    text =
        String.format(
            this.context.getString(R.string.text_format_kill_involvement),percent
        )
}

@BindingAdapter("item_match_game_items")
fun LinearLayout.setMatchGameItems(
    items: List<GameData.Item>
) {
    this.removeAllViews()

    for(i: Int in 0 until 6) {
        val itemBinding = DataBindingUtil.inflate<RowGameItemBinding>(
            LayoutInflater.from(this.context), R.layout.row_game_item, null, false
        )
        if(i < items.size) {
            itemBinding.item = items[i].imageUrl
        }
        this.addView(itemBinding.root)
    }
}

@BindingAdapter("item_match_game_ward")
fun ImageView.setMatchGameWard(
    items: List<GameData.Item>
) {
    Glide.with(this.context)
        .load(items.last().imageUrl)
        .apply(RequestOptions.bitmapTransform(MultiTransformation(CircleCrop())))
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("item_match_created_date")
fun TextView.setMatchCreatedDate(
    time: Long
) {
    val date = Date()
    val currentTime = (date.time / 1000L).toInt()
    val diffTime = currentTime - time
    text =
        when {
            diffTime < 60 * 60 -> {
                String.format(this.context.getString(R.string.time_format_minute), diffTime / 60)
            }
            diffTime < 60 * 60 * 24 -> {
                String.format(this.context.getString(R.string.time_format_hour), diffTime / 60 / 60)
            }
            diffTime < 60 * 60 * 24 * 30 -> {
                String.format(this.context.getString(R.string.time_format_day), diffTime / 60 / 60 / 24)
            }
            else -> {
                SimpleDateFormat("yy.MM.dd", Locale.KOREA).format(Date(time * 1000L))
            }
        }
}

@BindingAdapter("largest_multi_kill_string")
fun TextView.setLargestMultiKillString(
    item: String
) {
    visibility =
        if (item.isNotEmpty()) View.VISIBLE else View.GONE
    text = item
}