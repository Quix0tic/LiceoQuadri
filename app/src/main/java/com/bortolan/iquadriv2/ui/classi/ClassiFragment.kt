package com.bortolan.iquadriv2.ui.classi


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.api.SyncHelper
import com.bortolan.iquadriv2.ui.classi.adapter.ClassiAdapter
import com.bortolan.iquadriv2.ui.classi.viewModel.ClassiViewModel
import kotlinx.android.synthetic.main.fragment_classi.*

class ClassiFragment : Fragment() {
    private val viewModel: ClassiViewModel by lazy {
        ViewModelProviders.of(activity!!)[ClassiViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ClassiAdapter { classe, classiViewHolder ->
            startActivity(
                    Intent(activity!!, ClasseDettagliActivity::class.java).putExtra("transitionName", ViewCompat.getTransitionName(classiViewHolder.itemView)),
                    ActivityOptionsCompat
                            .makeSceneTransitionAnimation(activity!!,
                                    Pair.create(classiViewHolder.itemView, ViewCompat.getTransitionName(classiViewHolder.itemView).orEmpty())
                            )
                            .toBundle())
        }
        recycler.adapter = adapter

        viewModel.init(context!!)
        viewModel.isSendingRequest.observe(this, Observer {
            refresh.isRefreshing = it == true
        })
        viewModel.classi.observe(this, Observer {
            adapter.submitList(it)
        })

        refresh.setColorSchemeResources(R.color.colorPrimary)
        refresh.setOnRefreshListener { SyncHelper.syncClassi(context!!, viewModel) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.title = "Classi"
    }


}
