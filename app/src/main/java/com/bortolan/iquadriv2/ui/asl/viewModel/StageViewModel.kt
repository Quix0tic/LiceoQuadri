package com.bortolan.iquadriv2.ui.asl.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.data.db.MasterstageDao

class StageViewModel : ViewModel() {

    private lateinit var dao: MasterstageDao

    fun init(context: Context) {
        dao = DB.getInstance(context).masterstage()

    }

    val stages by lazy {
        LivePagedListBuilder(
                dao.getStageDataHolder()
                , 50).build()
    }

    val isSendingRequest = MutableLiveData<Boolean>()
}