package com.bortolan.iquadriv2.data.pojos

import androidx.annotation.StringDef
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Orari")
data class Orario(
        @PrimaryKey
        val id: String,
        val nome: String,
        @OrarioType val tipo: String,
        var url: String = ""
) {
    @StringDef(Orario.TYPE_AULA, Orario.TYPE_CLASSE, Orario.TYPE_PROF)
    annotation class OrarioType

    companion object {
        const val TYPE_PROF = "grProf"
        const val TYPE_CLASSE = "grClasse"
        const val TYPE_AULA = "grSalle"
    }
}