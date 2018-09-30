package com.bortolan.iquadriv2

import android.content.Context
import android.preference.PreferenceManager
import java.util.*

object PreferenceManager {

    private val connectedToMasterstage = "connectedToMasterstage"
    private val connectedToSpaggiari = "connectedToSpaggiari"
    private val classe = "classe"
    private val lastCheckedNews = "lastCheckedNews"
    private val circolariFilter = "circolariFilter"

    fun isConnectedToMasterstage(c: Context) = pref(c).getBoolean(connectedToMasterstage, false)
    fun setConnectedToMasterstage(c: Context, connected: Boolean) = pref(c).edit().putBoolean(connectedToMasterstage, connected).apply()

    fun isConnectedToSpaggiari(c: Context) = pref(c).getBoolean(connectedToSpaggiari, false)
    fun setConnectedToSpaggiari(c: Context, connected: Boolean) = pref(c).edit().putBoolean(connectedToSpaggiari, connected).apply()

    fun getClasse(c: Context) = pref(c).getString(classe, "")
    fun setClasse(c: Context, cl: String) = pref(c).edit().putString(classe, cl).apply()

    fun getLastCheckedNews(c: Context): Date = Date(pref(c).getLong(lastCheckedNews, 0))
    fun setLastCheckedNews(c: Context, date: Date) = pref(c).edit().putLong(lastCheckedNews, date.time).apply()

    fun getCircolariFilter(c: Context): MutableSet<String> = pref(c).getStringSet(circolariFilter, mutableSetOf("circolari"))
            ?: mutableSetOf("circolari")

    fun setCircolariFilter(c: Context, filter: Set<String>) = pref(c).edit().putStringSet(circolariFilter, filter).apply()

    private fun pref(c: Context?) = PreferenceManager.getDefaultSharedPreferences(c
            ?: throw NullPointerException("Context must not be null"))
}
