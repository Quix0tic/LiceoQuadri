package com.bortolan.iquadriv2.data.api

import android.annotation.SuppressLint
import android.content.Context
import com.bortolan.iquadriv2.data.api.parser.MasterstageParser
import com.bortolan.iquadriv2.data.api.parser.QuadriParser
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.ui.asl.viewModel.StageViewModel
import com.bortolan.iquadriv2.ui.classi.viewModel.ClassiViewModel
import com.crashlytics.android.Crashlytics
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SyncHelper {

    @SuppressLint("CheckResult")
    fun syncStage(c: Context, viewModel: StageViewModel) {
        viewModel.isSendingRequest.value = true
        MasterstageParser
                .getStages(MasterstageAPI.getInstance(c))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                // Only successful requests
                .filter { it.isSuccessful }
                .map { it.body().orEmpty() }
                // Emits single events from List<Int>
                .flatMap { Observable.fromIterable(it) }
                // Emits new api calls based on event
                .map { id ->
                    MasterstageParser.getStage(id, MasterstageAPI.getInstance(c))
                }
                .flatMap { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
                // Only successful requests
                .filter { it.isSuccessful }
                .map { it.body()!! }
                .toList()
                .toObservable()
                .subscribe({
                    DB.getInstance(c).masterstage().saveStages(it)
                }, Throwable::printStackTrace, {
                    viewModel.isSendingRequest.value = false
                })
    }

    @SuppressLint("CheckResult")
    fun syncClassi(c: Context, viewModel: ClassiViewModel) {
        viewModel.isSendingRequest.value = true
        QuadriParser
                .getStudenti(QuadriAPI.getInstance(c))
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.isSuccessful }
                .map { it.body()!! }
                .subscribe({
                    DB.getInstance(c).quadri().saveStudenti(it)
                }, Throwable::printStackTrace, {
                    viewModel.isSendingRequest.value = false
                })
    }

    @SuppressLint("CheckResult")
    fun syncProgetti(c: Context) {
        QuadriParser
                .getProgetti(QuadriAPI.getInstance(c))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter { it.isSuccessful }
                .map { it.body() }
                .flatMap { Observable.fromIterable(it) }
                .map { QuadriAPI.getInstance(c).getProgetto(it.url) }
                .flatMap { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
                .subscribe({

                },Throwable::printStackTrace,{

                })

    }

    @SuppressLint("CheckResult")
    fun syncOrari(c: Context) {
        QuadriParser.getOrari(QuadriAPI.getInstance(c))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.isSuccessful }
                .subscribe({
                    DB.getInstance(c).quadri().saveOrari(it.body()!!)
                }, Throwable::printStackTrace, {

                })

    }

    @SuppressLint("CheckResult")
    fun syncNotizie(context: Context) {
        QuadriParser.getNotizie(QuadriAPI.getInstance(context))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter { it.isSuccessful }
                .map { it.body()!! }
                .subscribe({
                    DB.getInstance(context).quadri().saveNotizie(it)
                }, Throwable::printStackTrace, {

                })
    }

    @SuppressLint("CheckResult")
    fun syncCircolari(context: Context) {
        QuadriParser.getCircolari()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    DB.getInstance(context).quadri().saveCircolari(it)
                }, {
                    Crashlytics.log("syncCircolari: " + it.message)
                }, {

                })
    }

    @SuppressLint("CheckResult")
    fun syncQuadrinews(context: Context) {
        QuadriParser.getQuadrinews(QuadriAPI.getInstance(context))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map { it.body()!! }
                .subscribe({
                    DB.getInstance(context).quadri().saveQuadrinews(it)
                }, Throwable::printStackTrace, {

                })
    }
}