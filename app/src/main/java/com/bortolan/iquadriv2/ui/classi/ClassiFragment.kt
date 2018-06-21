package com.bortolan.iquadriv2.ui.classi


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.api.SyncHelper
import com.bortolan.iquadriv2.ui.classi.adapter.ClassiAdapter
import com.bortolan.iquadriv2.ui.classi.viewModel.ClassiViewModel
import kotlinx.android.synthetic.main.fragment_stage.*

class ClassiFragment : Fragment() {
    val adapter = ClassiAdapter()
    val viewModel: ClassiViewModel by lazy {
        ViewModelProviders.of(activity!!)[ClassiViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Classi"

        recycler.adapter = adapter

        viewModel.init(context!!)
        viewModel.isSendingRequest.observe(this, Observer {
            refresh.isRefreshing = it == true
        })
        viewModel.classi.observe(this, Observer {
            adapter.setData(it.orEmpty())
            adapter.notifyDataSetChanged()
        })

        refresh.setColorSchemeResources(R.color.colorPrimary)
        refresh.setOnRefreshListener { SyncHelper.syncClassi(context!!, viewModel) }
    }


}
