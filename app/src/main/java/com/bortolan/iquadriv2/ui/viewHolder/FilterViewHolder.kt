package com.bortolan.iquadriv2.ui.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.adapter_filter.view.*

class FilterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_filter, parent, false)) {
    private val chipGroup = itemView.chip_group

    fun bind(items: Set<String>, checkedItems: Set<String>, onFilterChanged: (Set<String>) -> Unit) {
        chipGroup.removeAllViews()

        items.forEachIndexed { index, s ->
            val chip = Chip(chipGroup.context)
            chip.id = chipGroup.childCount
            chip.text = s
            chip.isCheckable = true
            chip.isCheckedIconVisible = true
            chip.setCheckedIconResource(R.drawable.ic_chip_check)
            if (checkedItems.contains(s)) {
                chip.isChecked = true
            }
            chip.setOnClickListener {
                it as Chip
                it.isChecked = true
                it.post {
                    onFilterChanged.invoke(getFilter())
                }
            }/*
            chip.setOnCheckedChangeListener { compoundButton, b ->
                onFilterChanged.invoke(getFilter())
            }*/
            chip.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            chipGroup.addView(chip, index)
        }
    }

    private fun getFilter(): Set<String> {
        val set = mutableSetOf<String>()
        for (i in 0 until chipGroup.childCount) {
            if ((chipGroup.getChildAt(i) as Chip).isChecked) {
                set.add((chipGroup.getChildAt(i) as Chip).text.toString().toLowerCase())
            }
        }
        return set
    }
}