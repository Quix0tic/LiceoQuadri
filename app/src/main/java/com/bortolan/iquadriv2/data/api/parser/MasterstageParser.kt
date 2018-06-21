package com.bortolan.iquadriv2.data.api.parser

import com.bortolan.iquadriv2.data.api.MasterstageAPI
import com.bortolan.iquadriv2.data.pojos.Presenza
import com.bortolan.iquadriv2.data.pojos.Stage
import io.reactivex.Observable
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object MasterstageParser {
    val simpleDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val hhmmss = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    /*

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


        (LISTA) PRESENZE:                   tr.has_original[id^=presenza_set-2-{x}]
               ID:                          input[input[type=hidden][name=presenza_set-2-{x}-id]]
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
    fun getCSRF_login(api: MasterstageAPI): Observable<Response<String>> {
        return api.getCSRF_login().map {
            if (it.isSuccessful) {
                return@map Response.success(Jsoup.parse(it.body()?.string().orEmpty()).selectFirst("input[name=csrfmiddlewaretoken]").`val`(), it.raw())
            } else {
                return@map Response.error<String>(it.errorBody()
                        ?: throw NullPointerException("errorBody must not be null when response is not successful"), it.raw())
            }
        }
    }

    fun getStages(api: MasterstageAPI): Observable<Response<List<Long>>> {
        return api.getStage().map {
            if (it.isSuccessful) {
                val html = Jsoup.parse(it.body()?.string().orEmpty())

                // Get deep info for each stage
                return@map Response.success(html.select("tr[class^= row] a").map {
                    Regex("\\d+").find(it.attr("href"))?.value?.toLong() ?: -1L
                }, it.raw())
            }


            return@map Response.error<List<Long>>(it.errorBody()
                    ?: throw NullPointerException("errorBody must not be null when response is not successful"), it.raw())
        }
    }

    fun getStage(id: Long, api: MasterstageAPI): Observable<Response<Stage>> {
        return api.getStage(id).map { t: Response<ResponseBody> ->
            if (t.isSuccessful) {
                val html = Jsoup.parse(t.body()?.string().orEmpty())
                val stage = Stage()

                stage.id = id
                stage.progetto = html.selectFirst("div.field-progetto p.form-control-static").text()
                stage.descrizione = html.selectFirst("div.field-descrizione p.form-control-static").text()
                stage.mansioni = html.selectFirst("div.field-mansioni p.form-control-static").text()
                stage.luogoSvolgimento = html.selectFirst("div.field-luogo_svolgimento p.form-control-static").text()
                stage.dataInizio = simpleDate.parse(html.selectFirst("div.field-data_inizio p.form-control-static").text())
                stage.dataFine = simpleDate.parse(html.selectFirst("div.field-data_fine p.form-control-static").text())
                stage.tutorScolastico = html.selectFirst("div.field-get_tutor_scolastico_display p.form-control-static").text()
                stage.tutorAziendale = html.selectFirst("div.field-get_tutor_aziendale_display p.form-control-static").text()
                stage.valutazioneStudente = html.selectFirst("#id_valutazione_studente").text()
                stage.orePreviste = html.selectFirst("div.field-numero_ore p").text().toInt()

                html.select("tr.has_original[id^=presenza_set-2-]").forEachIndexed { index, element ->
                    val presenza = Presenza()

                    presenza.id = html.selectFirst("input[type=hidden][name=presenza_set-2-$index-id]").`val`().toLong()
                    presenza.data = simpleDate.parse(element.selectFirst("input[id\$=data_presenza][value]").`val`())

                    val inizio = element.selectFirst("input[id\$=orario_inizio][value]").`val`()
                    val inizioTime = hhmmss.parse(inizio).time
                    presenza.inizio = (inizioTime / 1000f).roundToInt()

                    val fine = element.selectFirst("input[id\$=orario_fine][value]").`val`()
                    val fineTime = hhmmss.parse(fine).time
                    presenza.fine = (fineTime / 1000f).roundToInt()

                    presenza.attivita = element.selectFirst("textarea[id\$=attivita_svolta]").text()
                    presenza.convalida = element.selectFirst("td.field-convalida_tutor img").attr("alt").toLowerCase() == "true"

                    stage.presenze.add(presenza)

                }

                //TODO: Aggiungi valutazione stage
                return@map Response.success(stage, t.raw())
            }

            return@map Response.error<Stage>(t.errorBody()
                    ?: throw NullPointerException("errorBody must not be null when response is not successful"), t.raw())
        }
    }
}