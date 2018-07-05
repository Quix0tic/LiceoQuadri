package com.bortolan.iquadriv2.ui.classi

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.bortolan.iquadriv2.R
import kotlinx.android.synthetic.main.activity_classe_dettagli.*

class ClasseDettagliActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classe_dettagli)
        setSupportActionBar(toolbar)

        val trEnter = Slide()
        trEnter.slideEdge = Gravity.TOP
        trEnter.mode = Slide.MODE_IN
        trEnter.duration = 300
        trEnter.interpolator = FastOutSlowInInterpolator()
        trEnter.addTarget(toolbar)
        window.enterTransition = trEnter

        val trReturn = Slide()
        trReturn.slideEdge = Gravity.TOP
        trReturn.mode = Slide.MODE_OUT
        trReturn.duration = 250
        trReturn.interpolator = FastOutSlowInInterpolator()
        trReturn.addTarget(toolbar)

        window.returnTransition = trReturn

    }
}
