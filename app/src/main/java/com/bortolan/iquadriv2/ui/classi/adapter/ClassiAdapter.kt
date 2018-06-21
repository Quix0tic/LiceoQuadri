package com.bortolan.iquadriv2.ui.classi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.pojos.Classe
import com.bortolan.iquadriv2.ui.classi.viewHolder.ClassiViewHolder

class ClassiAdapter : RecyclerView.Adapter<ClassiViewHolder>() {
    private val data = mutableListOf<Classe>()

    fun setData(list: List<Classe>) {
        data.clear()
        data.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassiViewHolder {
        return ClassiViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_classe, parent, false))
    }

    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClassiViewHolder, position: Int) {
        val classe = data[position]

        holder.title.text = "${classe.grado}°"
        holder.content.text = "${classe.nStudenti} studenti"
    }
}