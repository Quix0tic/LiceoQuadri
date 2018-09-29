package com.bortolan.iquadriv2.ui.notizie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.api.QuadriAPI
import com.bortolan.iquadriv2.data.api.SyncHelper
import com.bortolan.iquadriv2.data.api.parser.QuadriParser
import com.bortolan.iquadriv2.ui.notizie.adapter.CircolariAdapter
import com.bortolan.iquadriv2.ui.notizie.adapter.NewsAdapter
import com.bortolan.iquadriv2.ui.notizie.adapter.QuadrinewsAdapter
import kotlinx.android.synthetic.main.fragment_notizie.*

//TODO: "Mostra altro"
class NotizieFragment : Fragment() {

    private val newsAdapter = NewsAdapter()
    private val circolariAdapter = CircolariAdapter()
    private val quadrinewsAdapter = QuadrinewsAdapter()
    private val viewModel by lazy {
        ViewModelProviders.of(this)[NotizieViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notizie, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Notizie"


        news_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        circolari_recycler.layoutManager = LinearLayoutManager(context)
        quadrinews_recycler.layoutManager = LinearLayoutManager(context)

        news_recycler.adapter = newsAdapter
        circolari_recycler.adapter = circolariAdapter
        quadrinews_recycler.adapter = quadrinewsAdapter

        syncNews()
        markAsRead()

        viewModel.getNotizie(context!!).observe(this, Observer { it ->
            newsAdapter.setData(it)
        })
        viewModel.getCircolari(context!!).observe(this, Observer { it ->
            circolariAdapter.setData(it)
        })
        viewModel.getQuadrinews(context!!).observe(this, Observer { it ->
            quadrinewsAdapter.setData(it)
        })
    }

    fun markAsRead() {

    }

    fun syncNews() {
        SyncHelper.syncNotizie(context!!)
        SyncHelper.syncCircolari(context!!)
        SyncHelper.syncQuadrinews(context!!)
    }
}
