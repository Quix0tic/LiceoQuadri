package com.bortolan.iquadriv2.data.api.parser

import android.annotation.SuppressLint
import com.bortolan.iquadriv2.data.api.QuadriAPI
import com.bortolan.iquadriv2.data.pojos.*
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

object QuadriParser {

    val circolareDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
    val quadrinewsDateFormat = SimpleDateFormat("d MMMM yy", Locale.ITALY)

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

    fun getNotizie(api: QuadriAPI): Observable<Response<List<Notizie>>> {
        return api.getHome().map {
            if (it.isSuccessful) {
                val html = Jsoup.parse(it.body()?.string().toString())
                val rows = html.select("table tbody table tbody tr")

                val notizie = rows
                        .map {
                            val url = it.select("img").attr("src")
                            var text = ""
                            for (el in it.select("span")) {
                                if (el.text().isNotEmpty()) {
                                    text = el.text()
                                    break
                                }
                            }
                            Notizie(0, url, text, it.text().substring(text.length).trim())
                        }

                Response.success(notizie, it.raw())
            } else {
                Response.error<List<Notizie>>(it.errorBody()!!, it.raw())
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getCircolari(): Observable<List<Circolari>> {
        return Observable.fromCallable {
            val html = Jsoup.connect("https://mail.liceoquadri.it/wp_circolari/wordpress/index.php/category/circolari/feed/").ignoreContentType(true).parser(Parser.xmlParser()).execute().parse()
            val rows = html.select("item")

            rows.map {
                Circolari(it.select("title").text(), it.select("description").text(), it.select("link").text(), circolareDateFormat.parse(it.select("pubDate").text()), Date())
            }
        }
    }

    fun getQuadrinews(api: QuadriAPI): Observable<Response<List<Quadrinews>>> {
        return api.getQuadrinews().map {
            if (it.isSuccessful) {
                val html = Jsoup.parse(it.body()?.string().toString())
                val rows = html.select("div.post-box-archive")

                val notizie = rows
                        .map {
                            Quadrinews(it.select("h4").text(), it.select("div.piccino").text(), quadrinewsDateFormat.parse(it.select("span.hdate").text()), it.select("a[href]").first().attr("href"))
                        }

                Response.success(notizie, it.raw())
            } else {
                Response.error<List<Quadrinews>>(it.errorBody()!!, it.raw())
            }
        }
    }
}