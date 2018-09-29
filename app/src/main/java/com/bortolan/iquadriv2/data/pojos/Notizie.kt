package com.bortolan.iquadriv2.data.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notizie")
data class Notizie(@PrimaryKey(autoGenerate = true) val id: Int = 0, val url: String, val text: String, val content: String)