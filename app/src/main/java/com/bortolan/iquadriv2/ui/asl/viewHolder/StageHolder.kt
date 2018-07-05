package com.bortolan.iquadriv2.ui.asl.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.ui.asl.adapter.StageAdapter
import com.google.android.material.chip.Chip

class StageHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val hours: TextView = view.findViewById(R.id.hours)
    val content: TextView = view.findViewById(R.id.content)
    val tutorScolastico: Chip = view.findViewById(R.id.tutor_scolastico)
    val tutorAziendale: Chip = view.findViewById(R.id.tutor_aziendale)

    fun bind(stage: StageAdapter.StageDataHolder?) {
        if (stage == null) {

        } else {
            title.text = stage.luogo
            content.text = stage.descrizione
            tutorScolastico.chipText = stage.tutorScolastico
            tutorAziendale.chipText = stage.tutorAziendale
            /*holder.tutorScolastico.setChipIconResource(R.drawable.ic_school_black_24dp)
            holder.tutorAziendale.setChipIconResource(R.drawable.ic_domain_black_24dp)
            holder.tutorScolastico.setChipIconSizeResource(R.dimen.chip_icon_size)
            holder.tutorAziendale.setChipIconSizeResource(R.dimen.chip_icon_size)*/
            hours.text = "${stage.ore}h"
        }
    }
}