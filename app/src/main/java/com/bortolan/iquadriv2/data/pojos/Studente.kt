package com.bortolan.iquadriv2.data.pojos

import androidx.room.Entity

@Entity(tableName = "Studenti", primaryKeys = ["cognome", "nome","classe"])
data class Studente(
        val classe: String,
        val grado: Int,
        val sezione: String,
        val indirizzo: String,
        val cognome: String,
        val nome: String
)