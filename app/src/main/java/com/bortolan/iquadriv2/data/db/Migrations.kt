package com.bortolan.iquadriv2.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val mig1 = object :Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {

        }
    }
}