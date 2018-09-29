package com.bortolan.iquadriv2.ui.notizie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.pojos.Circolari
import com.bortolan.iquadriv2.ui.viewHolder.MostraAltroViewHolder
import com.bortolan.iquadriv2.ui.viewHolder.TwoLinesHolder
import kotlinx.android.synthetic.main.adapter_circolare.view.*

class CircolariAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = mutableListOf<Circolari>()

    fun setData(list: List<Circolari>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            MostraAltroViewHolder(parent)
        } else TwoLinesHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_circolare, parent, false))
    }

    override fun getItemCount() = data.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) 1
        else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TwoLinesHolder) {
            holder.title.text = data[position].title
            holder.content.text = data[position].content
        } else {

        }
    }

}