package com.bortolan.iquadriv2.data.pojos

import androidx.room.Entity
import java.util.*

@Entity(primaryKeys = ["url", "date"])
data class Quadrinews(val title: String, val content: String, val date: Date, val url: String)