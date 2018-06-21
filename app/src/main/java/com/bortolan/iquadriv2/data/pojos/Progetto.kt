package com.bortolan.iquadriv2.data.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Progetti")
data class Progetto(
        @PrimaryKey
        val url: String,
        val nome: String
)