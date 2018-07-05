package com.bortolan.iquadriv2.ui.calendario


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.bortolan.iquadriv2.R
import kotlinx.android.synthetic.main.fragment_calendario.*

class CalendarioFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Calendario"

        if (savedInstanceState != null) {
            web_view.restoreState(savedInstanceState)
        } else {
            web_view.loadUrl("https://calendar.google.com/calendar/embed?showTitle=0&showTz=0&mode=AGENDA&wkst=2&bgcolor=%23FFFFFF&src=info%40liceoquadri.it&color=%2329527A&ctz=Europe%2FRome")
        }
        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //TODO()
            }
        }
        web_view.settings.javaScriptEnabled = true
        web_view.settings.allowContentAccess = false
        web_view.settings.allowFileAccess = false

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        web_view.saveState(outState)
    }

}
