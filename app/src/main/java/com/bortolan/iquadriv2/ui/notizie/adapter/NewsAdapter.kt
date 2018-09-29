package com.bortolan.iquadriv2.ui.notizie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.pojos.Notizie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_news.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    val data = mutableListOf<Notizie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false))
    }

    fun setData(d: List<Notizie>) {
        data.clear()
        data.addAll(d)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.title.text = data[position].text
        holder.content.text = data[position].content
        Glide.with(holder.itemView.context).load(data[position].url).into(holder.image).clearOnDetach()
    }

    class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image!!
        val title = view.title!!
        val content = view.content!!
    }
}