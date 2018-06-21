package com.bortolan.iquadriv2

import android.app.Application
import com.bortolan.iquadriv2.data.api.MasterstageAPI
import com.bortolan.iquadriv2.data.db.DB

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MasterstageAPI.getInstance(this)
        DB.getInstance(this)
    }
}