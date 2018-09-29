package com.bortolan.iquadriv2.ui.asl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.api.SyncHelper
import com.bortolan.iquadriv2.ui.asl.adapter.StageAdapter
import com.bortolan.iquadriv2.ui.asl.viewModel.StageViewModel
import kotlinx.android.synthetic.main.fragment_stage.*

class StageFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!)[StageViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refresh.setOnRefreshListener(this::download)
        refresh.setColorSchemeResources(R.color.colorPrimary)


        val adapter = StageAdapter()
        recycler.adapter = adapter
        viewModel.init(context!!)
        viewModel.stages.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.isSendingRequest.observe(this, Observer {
            refresh.isRefreshing = it == true
        })
        download()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.title = "ASL - Stage"
    }

    private fun download() {
        SyncHelper.syncStage(context!!, viewModel)
    }
}