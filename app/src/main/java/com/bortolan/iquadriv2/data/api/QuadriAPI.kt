package com.bortolan.iquadriv2.data.api

import android.content.Context
import com.bortolan.iquadriv2.SingletonHolder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface QuadriAPI {

    @GET("studenti/elenco-studenti/")
    fun getStudenti(): Observable<Response<ResponseBody>>

    @GET("progetti/")
    fun getProgetti(): Observable<Response<ResponseBody>>

    @GET("wp-content/archivio/orario/_ressource.js")
    fun getIDOrari(): Observable<Response<ResponseBody>>

    @GET("wp-content/archivio/orario/_grille.js")
    fun getLinkOrari(): Observable<Response<ResponseBody>>

    companion object : SingletonHolder<QuadriAPI, Context>({
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC


        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.liceoquadri.gov.it/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        retrofit.create(QuadriAPI::class.java)
    })
}