package com.bortolan.iquadriv2.ui.classi.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.data.db.QuadriDao

class ClassiViewModel : ViewModel() {
    val isSendingRequest = MutableLiveData<Boolean>()
    private lateinit var dao: QuadriDao

    val classi by lazy {
        LivePagedListBuilder(dao.getClassi(), 100).build()
    }

    fun init(c: Context) {
        dao = DB.getInstance(c).quadri()
    }
}