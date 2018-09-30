package com.bortolan.iquadriv2.ui.notizie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.pojos.Circolari
import com.bortolan.iquadriv2.ui.viewHolder.FilterViewHolder
import com.bortolan.iquadriv2.ui.viewHolder.MostraAltroViewHolder
import com.bortolan.iquadriv2.ui.viewHolder.TwoLinesHolder
import kotlinx.android.synthetic.main.adapter_circolare.view.*
import kotlinx.android.synthetic.main.adapter_mostra_altro.view.*

class CircolariAdapter(private val moreClick: (((RecyclerView.ViewHolder) -> Unit))?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = mutableListOf<Circolari>()
    var provider: Provider? = null
    var isShowMore: Boolean = true

    fun setData(list: List<Circolari>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    interface Provider {
        fun getFilter(): Set<String>
        fun getChecked(): Set<String>
        fun onFilterChanged(filter: Set<String>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            MostraAltroViewHolder(parent)
        } else if (viewType == -1) {
            FilterViewHolder(parent)
        } else TwoLinesHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_circolare, parent, false))
    }

    override fun getItemCount() = data.size + 1 + if (isShowMore) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            data.size + 1 -> 1
            0 -> -1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TwoLinesHolder -> {
                holder.title.text = data[position - 1].title
                holder.content.text = data[position - 1].content
            }
            is FilterViewHolder ->
                holder.bind(
                        provider?.getFilter().orEmpty(),
                        provider?.getChecked().orEmpty()) {
                    provider?.onFilterChanged(it)
                }
            else -> holder.itemView.altro.setOnClickListener { moreClick?.invoke(holder) }
        }
    }

}