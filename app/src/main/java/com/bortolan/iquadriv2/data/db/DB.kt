package com.bortolan.iquadriv2.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bortolan.iquadriv2.SingletonHolder
import com.bortolan.iquadriv2.data.db.converters.DateConverter
import com.bortolan.iquadriv2.data.pojos.*

@TypeConverters(DateConverter::class)
@Database(entities = [Stage::class, Presenza::class, Studente::class, Progetto::class, Orario::class, Notizie::class, Circolari::class, Quadrinews::class], version = 11, exportSchema = true)
abstract class DB : RoomDatabase() {
    abstract fun masterstage(): MasterstageDao
    abstract fun quadri(): QuadriDao

    companion object : SingletonHolder<DB, Context>({
        Room.databaseBuilder(it.applicationContext,
                DB::class.java, "Sample.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    })
}