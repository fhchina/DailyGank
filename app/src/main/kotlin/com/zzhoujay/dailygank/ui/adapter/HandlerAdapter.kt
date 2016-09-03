package com.zzhoujay.dailygank.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.zzhoujay.dailygank.R
import com.zzhoujay.dailygank.util.DateKit
import kotlinx.android.synthetic.main.item_handler.view.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick
import java.util.*

/**
 * Created by zhou on 16-3-12.
 */
class HandlerAdapter(val context: Context, val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onHandlerClickListener: ((i: Int) -> Unit)? = null
    var onListClickListener: ((v: View, i: Int) -> Unit)? = null
    var onListLongClickListener: (() -> Unit)? = null

    var title: String? = null
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    val dataObserver: RecyclerView.AdapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            notifyItemRangeChanged(fromPosition + 1, toPosition + 1 + itemCount)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            notifyItemRangeInserted(positionStart + 1, itemCount)
        }

        override fun onChanged() {
            super.onChanged()
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            notifyItemRangeRemoved(positionStart + 1, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            notifyItemRangeChanged(positionStart + 1, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            notifyItemRangeChanged(positionStart + 1, itemCount, payload)
        }
    }

    init {
        adapter.registerAdapterDataObserver(dataObserver)
    }


    override fun onCreateViewHolder(p0: ViewGroup?, type: Int): RecyclerView.ViewHolder? {
        if (type == type_handler) {
            val holder = Holder(LayoutInflater.from(context).inflate(R.layout.item_handler, p0, false))
            holder.handlerClickListener = {
                onHandlerClickListener?.invoke(it)
            }
            holder.listClickListener = { v, i ->
                onListClickListener?.invoke(v, i)
            }
            holder.listLongClickListener = {
                onListLongClickListener?.invoke()
            }
            return holder
        }
        return adapter.onCreateViewHolder(p0, type)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position == 0 && holder is Holder) {
            holder.title.text = title ?: DateKit.friendlyFormatDate(Date())
        } else {
            adapter.onBindViewHolder(holder, position - 1)
        }
    }

    override fun getItemCount(): Int {
        return adapter.itemCount + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return type_handler
        }
        return adapter.getItemViewType(position)
    }

    class Holder(val root: View) : RecyclerView.ViewHolder(root) {
        val title: TextView
        val handler: View

        var handlerClickListener: ((i: Int) -> Unit)? = null
        var listClickListener: ((v: View, i: Int) -> Unit)? = null
        var listLongClickListener: (() -> Unit)? = null

        init {
            handler = root.root_handler
            title = root.handler_title

            handler.onClick {
                handlerClickListener?.invoke(adapterPosition)
            }

        }

    }


    companion object {
        const val type_handler = 0x1234
    }
}