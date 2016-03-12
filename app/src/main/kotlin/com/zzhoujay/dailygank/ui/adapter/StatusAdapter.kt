package com.zzhoujay.dailygank.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.util.*

/**
 * Created by zhou on 16-3-12.
 */
class StatusAdapter(vararg adapterMap: Pair<String, RecyclerView.Adapter<RecyclerView.ViewHolder>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapters: HashMap<String, RecyclerView.Adapter<RecyclerView.ViewHolder>>
    private var currAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    init {
        adapters = HashMap(adapterMap.size)
        for (v in adapterMap) {
            adapters[v.first] = v.second
        }
        if (adapterMap.size > 0) {
            currAdapter = adapterMap[0].second
        }
    }

    fun switch(status: String) {
        val adapter = adapters[status]
        currAdapter = adapter
        notifyDataSetChanged()
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


}

