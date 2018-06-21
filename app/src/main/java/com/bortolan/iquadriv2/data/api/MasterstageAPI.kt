package com.bortolan.iquadriv2.data.api

import android.content.Context
import com.bortolan.iquadriv2.SingletonHolder
import com.bortolan.iquadriv2.data.api.cookie.PersistentCookieStore
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

interface MasterstageAPI {

    @GET("login/?next=/quadri-vi/studente/")
    fun getCSRF_login(): Observable<Response<ResponseBody>>

    @POST("login/?next=/quadri-vi/studente/")
    @FormUrlEncoded
    fun login(@Field("username") user: String, @Field("password") password: String, @Field("csrfmiddlewaretoken") token: String): Observable<Response<ResponseBody>>

    @POST("logout")
    fun logout(): Observable<Response<ResponseBody>>

    @GET("progetti/stage/")
    fun getStage(): Observable<Response<ResponseBody>>

    @GET("progetti/stage/{id}/change")
    fun getStage(@Path("id") id: Long): Observable<Response<ResponseBody>>

    /*
        @GET("progetti/stage/{id}/stampaadesionealternanza")
        abstract fun stampaAdesione(@Path("id") id: Int): Observable<Response<ResponseBody>>

        @GET("progetti/stage/{id}/stampaattestato")
        abstract fun stampaAttestato(@Path("id") id: Int): Observable<Response<ResponseBody>>

        @GET("progetti/stage/{id}/stampafogliopresenze")
        abstract fun stampaFoglioPresenze(@Path("id") id: Int): Observable<Response<ResponseBody>>

        @GET("progetti/stage/{id}/stampavalutazionestudente")
        abstract fun stampaValutazioneStudente(@Path("id") id: Int): Observable<Response<ResponseBody>>

    */
    companion object : SingletonHolder<MasterstageAPI, Context>({
        val cookieHandler = CookieManager(
                PersistentCookieStore(it), CookiePolicy.ACCEPT_ALL)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC


        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(5, TimeUnit.SECONDS)
                .cookieJar(JavaNetCookieJar(cookieHandler))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://alternanza.registroelettronico.com/quadri-vi/studente/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        retrofit.create(MasterstageAPI::class.java)
    })
}