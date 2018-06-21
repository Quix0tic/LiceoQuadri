package com.bortolan.iquadriv2.ui.asl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.ui.asl.viewHolder.StageHolder

class StageAdapter : RecyclerView.Adapter<StageHolder>() {

    data class StageDataHolder(
            val id: Long,
            val luogo: String,
            val descrizione: String,
            val tutorScolastico: String,
            val tutorAziendale: String,
            val ore: Int
    )

    private val data = mutableListOf<StageDataHolder>()

    fun setData(stages: List<StageDataHolder>) {
        data.clear()
        data.addAll(stages)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StageHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_stage, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: StageHolder, position: Int) {
        val stage = data[position]

        holder.title.text = stage.luogo
        holder.content.text = stage.descrizione
        holder.tutorScolastico.chipText = stage.tutorScolastico
        holder.tutorAziendale.chipText = stage.tutorAziendale
        /*holder.tutorScolastico.setChipIconResource(R.drawable.ic_school_black_24dp)
        holder.tutorAziendale.setChipIconResource(R.drawable.ic_domain_black_24dp)
        holder.tutorScolastico.setChipIconSizeResource(R.dimen.chip_icon_size)
        holder.tutorAziendale.setChipIconSizeResource(R.dimen.chip_icon_size)*/
        holder.hours.text = "${stage.ore}h"
    }
}