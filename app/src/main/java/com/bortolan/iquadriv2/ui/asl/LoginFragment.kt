package com.bortolan.iquadriv2.ui.asl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bortolan.iquadriv2.PreferenceManager
import com.bortolan.iquadriv2.R
import com.bortolan.iquadriv2.data.api.MasterstageAPI
import com.bortolan.iquadriv2.data.api.SyncHelper
import com.bortolan.iquadriv2.ui.MainActivity
import com.bortolan.iquadriv2.ui.asl.viewModel.LoginViewModel
import com.bortolan.iquadriv2.ui.asl.viewModel.StageViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_login_asl.*
import org.jsoup.Jsoup


class LoginFragment : Fragment() {
    private val api by lazy {
        MasterstageAPI.getInstance(context!!)
    }
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!)[LoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_asl, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "MasterStage"

        viewModel.init(context!!)
        viewModel.token.observe(this, Observer {
            send.isEnabled = !it.isNullOrEmpty()
        })
        viewModel.isSendingRequest.observe(this, Observer {
            with(it == true) {
                loading.visibility = if (this) View.VISIBLE else View.GONE
                send.visibility = if (this) View.INVISIBLE else View.VISIBLE
                field1.isEnabled = !this
                field2.isEnabled = !this
            }
        })


        send.setOnClickListener {
            login(field1.editText?.text.toString(), field2.editText?.text.toString(), viewModel.token.value.orEmpty())
        }

        info.setOnClickListener {
            TODO()
        }

        field1.error = ""
        field2.error = ""
        field1.editText?.setText(viewModel.username)
        field2.editText?.setText(viewModel.password)
    }

    override fun onStop() {
        viewModel.username = field1.editText?.text.toString()
        viewModel.password = field2.editText?.text.toString()
        super.onStop()
    }

    fun login(username: String, password: String, csrf: String) {
        field1.error = if (username.isEmpty()) "Campo obbligatorio" else ""
        field2.error = if (password.isEmpty()) "Campo obbligatorio" else ""

        if (username.isEmpty() || password.isEmpty()) {
            postLogin(false)
        } else {
            viewModel.isSendingRequest.value = true
            api.login(username, password, csrf)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        postLogin(
                                it.isSuccessful &&
                                        Jsoup.parse(it.body()?.string().orEmpty()).selectFirst("p.errornote") == null)
                    }
        }
    }

    fun postLogin(success: Boolean) {
        PreferenceManager.setConnectedToMasterstage(context, success)
        viewModel.isSendingRequest.value = false

        if (success) {
            SyncHelper.syncStage(context!!, ViewModelProviders.of(activity!!)[StageViewModel::class.java])
            MainActivity.showFragment(fragmentManager!!, StageFragment())
        } else {
            field2.editText?.setText("")
        }
    }
}
