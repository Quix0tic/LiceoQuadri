package com.bortolan.iquadriv2

import android.app.Application
import com.bortolan.iquadriv2.data.api.MasterstageAPI
import com.bortolan.iquadriv2.data.db.DB
import com.google.firebase.FirebaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MasterstageAPI.getInstance(this)
        DB.getInstance(this)

        FirebaseApp.initializeApp(this)
    }
}