package me.nyaken.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.recyclerview.scrollEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy

class CustomRecycler: RecyclerView {
    private var pastVisiblesItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun setLoadListenerForLinear(onLoadMore: () -> Unit) =
        this.scrollEvents()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                visibleItemCount = this.layoutManager!!.childCount
                totalItemCount = this.layoutManager!!.itemCount
                pastVisiblesItems = (this.layoutManager!! as LinearLayoutManager).findFirstVisibleItemPosition()

                if (totalItemCount > 0 && visibleItemCount + pastVisiblesItems >= totalItemCount - 5) {
                    onLoadMore()
                }
            }

}