package com.bortolan.iquadriv2.ui.notizie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.*
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.ui.notizie.adapter.CircolariAdapter
import kotlinx.android.synthetic.main.fragment_show_more.*

class ShowMoreFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(activity!!)[NotizieViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_more, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //enterTransition = TransitionSet().addTransition(Fade(Fade.MODE_IN)).setDuration(2000).setInterpolator(FastOutSlowInInterpolator())
        //exitTransition = TransitionSet().addTransition(Fade(Fade.MODE_OUT)).setDuration(2000).setInterpolator(FastOutSlowInInterpolator())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
