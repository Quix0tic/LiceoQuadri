package com.bortolan.iquadriv2.ui.classi.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.data.db.QuadriDao
import com.bortolan.iquadriv2.data.pojos.Classe

class ClassiViewModel : ViewModel() {
    val isSendingRequest = MutableLiveData<Boolean>()
    private lateinit var dao: QuadriDao

    val classi: LiveData<List<Classe>> by lazy {
        dao.getClassi()
    }

    fun init(c: Context) {
        dao = DB.getInstance(c).quadri()
    }
}