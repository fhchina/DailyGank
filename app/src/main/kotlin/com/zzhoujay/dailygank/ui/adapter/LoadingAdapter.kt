package com.zzhoujay.dailygank.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzhoujay.dailygank.R

/**
 * Created by zhou on 16-3-12.
 */
class LoadingAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): Holder? {
        return Holder(LayoutInflater.from(context).inflate(R.layout.layout_loading, p0, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, p1: Int) {
    }

    class Holder(val root: View) : RecyclerView.ViewHolder(root) {

    }
}