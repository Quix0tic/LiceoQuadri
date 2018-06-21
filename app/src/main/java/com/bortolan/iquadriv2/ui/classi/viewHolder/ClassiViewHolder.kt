package com.bortolan.iquadriv2.ui.classi.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R

class ClassiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val content: TextView = view.findViewById(R.id.content)
}