package com.bortolan.iquadriv2.ui.asl.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bortolan.iquadriv2.data.api.MasterstageAPI
import com.bortolan.iquadriv2.data.api.parser.MasterstageParser
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginViewModel : ViewModel() {
    val isSendingRequest = MutableLiveData<Boolean>()
    val token = MutableLiveData<String>()
    var username = ""
    var password = ""
    lateinit var api: MasterstageAPI

    fun init(context: Context) {
        api = MasterstageAPI.getInstance(context)

        if (token.value.isNullOrEmpty()) {
            isSendingRequest.value = true
            MasterstageParser
                    .getCSRF_login(api)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        isSendingRequest.value = false
                        if (it.isSuccessful) {
                            token.value = it.body().toString()
                        }
                    }
        }
    }
}