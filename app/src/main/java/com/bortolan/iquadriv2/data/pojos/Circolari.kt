package com.bortolan.iquadriv2.data.pojos

import androidx.room.Entity
import java.util.*

@Entity(tableName = "Circolari", primaryKeys = ["title", "date"])
data class Circolari(val title: String, val content: String, val url: String, val date: Date, val creationDate: Date)