package com.bortolan.iquadriv2.data.pojos

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Stage")
data class Stage(/*

        GET select2/fields/auto.json?field_id={field_id}
        GET progetti/stage/{id}/change/

        NOME PROGETTO:              div.field-progetto                          p.form-control-static
        DESCRIZIONE:                div.field-descrizione                       p.form-control-static
        MANSIONI:                   div.field-mansioni                          p.form-control-static
        LUOGO SVOLGIMENTO:          div.field-luogo_svolgimento                 p.form-control-static
        DATA INIZIO (gg/MM/aaaa):   div.field-data_inizio                       p.form-control-static
        DATA FINE (gg/MM/aaaa):     div.field-data_fine                         p.form-control-static
        ORARIO INIZIO (hh:mm):      div.field-orario_inizio                     p.form-control-static
        ORARIO FINE (hh:mm):        div.field-orario_fine                       p.form-control-static
        TUTOR SCOLASTICO:           div.field-get_tutor_scolastico_display      p.form-control-static
        TUTOR AZIENDALE:            div.field-get_tutor_aziendale_display       p.form-control-static


        (LISTA) PRESENZE:                   tr.has_original[id^=presenza_set-2-]
               DATA:                        input[id$=data_presenza]            [value]
               ORARIO INIZIO (hh:MM:ss):    input[id$=orario_inizio]            [value]
               ORARIO FINE (hh:MM:ss):      input[id$=orario_fine]              [value]
               ATTIVITA' SVOLTA             textarea[id$=attivita_svolta]
               CONVALIDA TUTOR (True/False) td.field-convalida_tutor img      [alt]


        VALUTAZIONE STUDENTE:       #id_valutazione_studente


        (LISTA) VALUTAZIONE STAGE STUDENTE:     tr.has_original[id^=valutazionestagestudente_set-]
                QUESITO:                        td.field-quesito
                {field_id}:                     select[data-field_id]       [data-field_id]
                ID RISPOSTA:                    option[selected]            [value]
                RISPOSTA:                       option[selected]
                NOTE:                           textarea[id$=note]



             */
        @PrimaryKey(autoGenerate = false)
        var id: Long = 0,
        var progetto: String = "",
        var descrizione: String = "",
        var mansioni: String = "",
        var luogoSvolgimento: String = "",
        var dataInizio: Date = Date(0),
        var dataFine: Date = Date(0),
        var tutorScolastico: String = "",
        var tutorAziendale: String = "",
        var valutazioneStudente: String = "",
        var orePreviste: Int = 0,
        @Ignore
        val presenze: MutableList<Presenza> = mutableListOf()
        //val valutazioneStage: MutableList<Quesito> = mutableListOf()
)