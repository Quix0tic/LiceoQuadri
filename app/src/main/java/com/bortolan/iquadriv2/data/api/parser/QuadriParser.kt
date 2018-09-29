package com.bortolan.iquadriv2.data.api.parser

import com.bortolan.iquadriv2.data.api.QuadriAPI
import com.bortolan.iquadriv2.data.pojos.Orario
import com.bortolan.iquadriv2.data.pojos.Progetto
import com.bortolan.iquadriv2.data.pojos.Studente
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.jsoup.Jsoup
import retrofit2.Response

object QuadriParser {

    fun getStudenti(api: QuadriAPI): Observable<Response<List<Studente>>> {
        return api
                .getStudenti()
                .map {
                    if (it.isSuccessful) {
                        val html = Jsoup.parse(it.body()?.string().toString())
                        val rows = html.select("div.postentry table tbody tr")

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
                        Response.error<List<Studente>>(it.errorBody()!!, it.raw())
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
                                            it.attr("href").split("/").last(),
                                            it.text()
                                    )
                                }

                        Response.success(progetti, it.raw())
                    } else {
                        Response.error<List<Progetto>>(it.errorBody()!!, it.raw())
                    }
                }
    }

    fun getOrari(api: QuadriAPI): Observable<Response<List<Orario>>> {
        return Observable.zip(api.getIDOrari(), api.getLinkOrari(), BiFunction { res1, res2 ->
            if (res1.isSuccessful && res2.isSuccessful) {
                val regexIDS = Regex("\"(gr\\w+)\",\"([^\"]+)\",\"([^\"]+)\"")
                val orari = regexIDS.findAll(res1.body()!!.string()).map {
                    Orario(it.groupValues[3], it.groupValues[2], it.groupValues[1])
                }.toList()

                val regexURLS = Regex("\"(\\w+/ed(.{8})[^\"]+)\"")

                regexURLS.findAll(res2.body()!!.string()).forEach {
                    val id = it.groupValues[2]
                    val url = it.groupValues[1]

                    val orario = orari.first { orario -> orario.id == id }
                    orario.url = url
                }

                return@BiFunction Response.success(orari)
            } else {
                return@BiFunction Response.error(res1.errorBody()!!, res1.raw())
            }
        })
    }
}