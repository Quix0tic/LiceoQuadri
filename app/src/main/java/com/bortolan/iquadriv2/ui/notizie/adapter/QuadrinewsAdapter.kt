package com.bortolan.iquadriv2.ui.notizie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.pojos.Quadrinews
import com.bortolan.iquadriv2.ui.viewHolder.MostraAltroViewHolder
import com.bortolan.iquadriv2.ui.viewHolder.TwoLinesHolder
import kotlinx.android.synthetic.main.adapter_mostra_altro.view.*

class QuadrinewsAdapter(private val moreClick: ((RecyclerView.ViewHolder) -> Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = mutableListOf<Quadrinews>()
    var isShowMore: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            MostraAltroViewHolder(parent)
        } else {
            TwoLinesHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_circolare, parent, false))
        }
    }

    override fun getItemCount() = data.size + if (isShowMore) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TwoLinesHolder) {
            holder.title.text = data[position].title
            holder.content.text = data[position].content
        } else {
            holder.itemView.altro.setOnClickListener { moreClick?.invoke(holder) }
        }
    }

    fun setData(it: List<Quadrinews>) {
        data.clear()
        data.addAll(it)
        notifyDataSetChanged()
    }
}