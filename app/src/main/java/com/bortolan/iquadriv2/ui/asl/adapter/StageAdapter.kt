package com.bortolan.iquadriv2.ui.asl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.ui.asl.viewHolder.StageHolder

class StageAdapter : PagedListAdapter<StageAdapter.StageDataHolder, StageHolder>(
        object : DiffUtil.ItemCallback<StageDataHolder>() {
            override fun areItemsTheSame(oldItem: StageDataHolder, newItem: StageDataHolder) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: StageDataHolder, newItem: StageDataHolder) = oldItem.luogo == newItem.luogo && oldItem.descrizione == newItem.descrizione
        }
) {

    data class StageDataHolder(
            val id: Long,
            val luogo: String,
            val descrizione: String,
            val tutorScolastico: String,
            val tutorAziendale: String,
            val ore: Int
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StageHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_stage, parent, false))

    override fun onBindViewHolder(holder: StageHolder, position: Int) {
        val stage = getItem(position)
        holder.bind(stage)
    }
}