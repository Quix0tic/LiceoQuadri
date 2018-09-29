package com.bortolan.iquadriv2.ui.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_circolare.view.*


class TwoLinesHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.title!!
    val content = view.content!!
}