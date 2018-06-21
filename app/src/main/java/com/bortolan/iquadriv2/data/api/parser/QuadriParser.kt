package com.bortolan.iquadriv2.data.api.parser

import com.bortolan.iquadriv2.data.api.QuadriAPI
import com.bortolan.iquadriv2.data.pojos.Progetto
import com.bortolan.iquadriv2.data.pojos.Studente
import io.reactivex.Observable
import org.jsoup.Jsoup
import retrofit2.Response

object QuadriParser {

    fun getStudenti(api: QuadriAPI): Observable<Response<List<Studente>>> {
        return api
                .getStudenti()
                .map {
                    if (it.isSuccessful) {
                        val html = Jsoup.parse(it.body()?.string().toString())
                        val rows = html.select("table tbody tr")

                        // Rimuovi intestazione
                        rows.removeAt(0)
                        val studenti = rows.filter { it.child(0).text().isNotEmpty() }
                                .map {
                                    val classe = it.child(0).text()
                                    Studente(
                                            classe,
                                            classe.substring(0, 1).toInt(),
                                            classe.substring(1, 2),
                                            classe.substring(2),
                                            it.child(1).text(),
                                            it.child(2).text()
                                    )
                                }

                        Response.success<List<Studente>>(studenti, it.raw())
                    } else {
                        Response.error<List<Studente>>(it.errorBody()
                                ?: throw NullPointerException("errorBody must not be null when response is not successful"), it.raw())
                    }
                }
    }

    fun getProgetti(api: QuadriAPI): Observable<Response<List<Progetto>>> {
        return api.getProgetti()
                .map {
                    if (it.isSuccessful) {
                        val html = Jsoup.parse(it.body()?.string().toString())
                        val rows = html.select("#menu-progetti a")

                        val progetti = rows
                                .map {
                                    Progetto(
                                            it.attr("href"),
                                            it.text()
                                    )
                                }

                        Response.success(progetti, it.raw())
                    } else {
                        Response.error<List<Progetto>>(it.errorBody()
                                ?: throw NullPointerException("errorBody must not be null when response is not successful"), it.raw())
                    }
                }
    }

}