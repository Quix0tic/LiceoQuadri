package com.bortolan.iquadriv2.ui.asl.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bortolan.iquadriv2.ui.asl.adapter.StageAdapter
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.data.db.MasterstageDao

class StageViewModel : ViewModel() {

    private lateinit var dao: MasterstageDao

    fun init(context: Context) {
        dao = DB.getInstance(context).masterstage()

    }

    val stages: LiveData<List<StageAdapter.StageDataHolder>> by lazy {
        Transformations.map(dao.getStageInclusive(), { stages ->
            stages.map {
                StageAdapter.StageDataHolder(it.id, it.luogoSvolgimento, it.descrizione.substring(0, 200).plus("..."), it.tutorScolastico, it.tutorAziendale, it.presenze.foldRight(0, { presenza, acc -> acc + ((presenza.fine - presenza.inizio) / 3600) }))
            }

        })
    }

    val isSendingRequest = MutableLiveData<Boolean>()
}