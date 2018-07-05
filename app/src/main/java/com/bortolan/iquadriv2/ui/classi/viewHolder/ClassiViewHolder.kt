package com.bortolan.iquadriv2.ui.classi.viewHolder

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.pojos.Classe

class ClassiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title)
    val content: TextView = view.findViewById(R.id.content)

    @SuppressLint("SetTextI18n")
    fun bind(classe: Classe?, onClick: (Classe, ClassiViewHolder) -> Unit) {
        itemView.transitionName = "view"
        if (classe == null) {
            title.text = ""
            content.text = ""
            itemView.setOnClickListener(null)
        } else {
            title.text = "${classe.grado}°"
            content.text = "${classe.nStudenti} studenti"
            itemView.setOnClickListener {
                onClick.invoke(classe, this)
            }
        }
    }
}