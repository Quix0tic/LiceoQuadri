package com.bortolan.iquadriv2.data.api

import android.annotation.SuppressLint
import android.content.Context
import com.bortolan.iquadriv2.data.api.parser.MasterstageParser
import com.bortolan.iquadriv2.data.api.parser.QuadriParser
import com.bortolan.iquadriv2.data.db.DB
import com.bortolan.iquadriv2.ui.asl.viewModel.StageViewModel
import com.bortolan.iquadriv2.ui.classi.viewModel.ClassiViewModel
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

    fun syncProgetti(c: Context) {
        TODO()
    }

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
}