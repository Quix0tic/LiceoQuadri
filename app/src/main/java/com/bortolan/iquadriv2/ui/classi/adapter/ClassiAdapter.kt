package com.bortolan.iquadriv2.ui.classi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.pojos.Classe
import com.bortolan.iquadriv2.ui.classi.viewHolder.ClassiViewHolder

class ClassiAdapter(val onClick: (Classe, ClassiViewHolder) -> Unit)
    : PagedListAdapter<Classe, ClassiViewHolder>(
        object : DiffUtil.ItemCallback<Classe>() {
            override fun areItemsTheSame(oldItem: Classe, newItem: Classe) = oldItem.grado == newItem.grado
            override fun areContentsTheSame(oldItem: Classe, newItem: Classe) = oldItem.nStudenti == newItem.nStudenti
        }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassiViewHolder {
        return ClassiViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_classe, parent, false))
    }

    override fun onBindViewHolder(holder: ClassiViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, onClick)
    }

}