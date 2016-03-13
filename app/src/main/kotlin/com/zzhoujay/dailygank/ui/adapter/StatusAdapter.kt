package com.zzhoujay.dailygank.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.util.*

/**
 * Created by zhou on 16-3-12.
 */
class StatusAdapter(vararg adapterMap: Pair<Status, RecyclerView.Adapter<RecyclerView.ViewHolder>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapters: HashMap<String, RecyclerView.Adapter<RecyclerView.ViewHolder>>
    private var currAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    var currStatus: Status

    val dataObserver: RecyclerView.AdapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            notifyItemRangeChanged(fromPosition, toPosition + itemCount)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onChanged() {
            super.onChanged()
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            notifyItemRangeChanged(positionStart, itemCount, payload)
        }
    }

    init {
        if (adapterMap.size > 0) {
            currAdapter = adapterMap[0].second
            currStatus = adapterMap[0].first
        } else {
            throw RuntimeException("至少需要一个元素")
        }
        adapters = HashMap(adapterMap.size)
        for (v in adapterMap) {
            adapters[v.first.name] = v.second
            v.second.registerAdapterDataObserver(dataObserver)
        }
    }

    fun switch(status: Status) {
        if (!status.equals(currStatus)) {
            val adapter = adapters[status.name]
            currAdapter = adapter
            currStatus = status
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, p1: Int) {
        currAdapter?.onBindViewHolder(p0, p1)
    }

    override fun getItemCount(): Int {
        return currAdapter?.itemCount ?: 0
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RecyclerView.ViewHolder? {
        return currAdapter?.onCreateViewHolder(p0, p1)
    }

    override fun getItemViewType(position: Int): Int {
        return currAdapter?.hashCode() ?: super.getItemViewType(position)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver?) {
        super.registerAdapterDataObserver(observer)
    }
}

enum class Status {
    normal, loading, date
}

