package com.bortolan.iquadriv2.ui.notizie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.data.pojos.Circolari
import com.bortolan.iquadriv2.data.pojos.Notizie
import com.bortolan.iquadriv2.data.pojos.Quadrinews

class NotizieViewModel : ViewModel() {

    val mapCircolari = mutableMapOf<Int, LiveData<List<Circolari>>>()

    fun getNotizie(context: Context): LiveData<List<Notizie>> {
        return DB.getInstance(context).quadri().getNotizie()
    }

    fun getCircolari(context: Context, limit: Int): LiveData<List<Circolari>> {
        if (mapCircolari[limit] == null) {
            mapCircolari[limit] = DB.getInstance(context).quadri().getCircolari(limit)
        }
        return mapCircolari[limit]!!
    }

    fun getQuadrinews(context: Context): LiveData<List<Quadrinews>> {
        return DB.getInstance(context).quadri().getQuadrinews(3)
    }
}
