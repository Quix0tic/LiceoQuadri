package com.bortolan.iquadriv2.data.pojos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Presenze")
data class Presenza(
        @PrimaryKey(autoGenerate = false) var id: Long = 0,
        var data: Date = Date(0),
        /**
         * inizio e fine sono indicati in secondi (hh:mm:ss)
         */
        var inizio: Int = 0,
        var fine: Int = 0,
        var attivita: String = "",
        var convalida: Boolean = false,
        @ForeignKey(entity = Stage::class, parentColumns = ["id"], childColumns = ["stageId"], onDelete = ForeignKey.CASCADE)
        var stageId: Long = -1
)