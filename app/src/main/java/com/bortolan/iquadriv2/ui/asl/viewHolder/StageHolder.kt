package com.bortolan.iquadriv2.ui.asl.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.google.android.material.chip.Chip

class StageHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val hours: TextView = view.findViewById(R.id.hours)
    val content: TextView = view.findViewById(R.id.content)
    val tutorScolastico: Chip = view.findViewById(R.id.tutor_scolastico)
    val tutorAziendale: Chip = view.findViewById(R.id.tutor_aziendale)
}