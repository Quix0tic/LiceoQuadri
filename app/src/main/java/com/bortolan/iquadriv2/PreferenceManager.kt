package com.bortolan.iquadriv2

import android.content.Context
import android.preference.PreferenceManager
import java.util.*

object PreferenceManager {

    private val connectedToMasterstage = "connectedToMasterstage"
    private val classe = "classe"
    private val lastCheckedNews = "lastCheckedNews"

    fun isConnectedToMasterstage(c: Context) = pref(c).getBoolean(connectedToMasterstage, false)
    fun setConnectedToMasterstage(c: Context?, connected: Boolean) = pref(c).edit().putBoolean(connectedToMasterstage, connected).apply()

    fun getClasse(c: Context) = pref(c).getString(classe, "")
    fun setClasse(c: Context, cl: String) = pref(c).edit().putString(classe, cl).apply()

    fun getLastCheckedNews(c: Context): Date = Date(pref(c).getLong(lastCheckedNews, 0))
    fun setLastCheckedNews(c: Context, date: Date) = pref(c).edit().putLong(lastCheckedNews, date.time).apply()

    private fun pref(c: Context?) = PreferenceManager.getDefaultSharedPreferences(c
            ?: throw NullPointerException("Context must not be null"))
}
