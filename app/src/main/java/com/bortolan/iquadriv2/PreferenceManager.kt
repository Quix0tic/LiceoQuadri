package com.bortolan.iquadriv2

import android.content.Context
import android.preference.PreferenceManager

object PreferenceManager {

    private val connectedToMasterstage = "connectedToMasterstage"
    private val classe = "classe"

    fun isConnectedToMasterstage(c: Context) = pref(c).getBoolean(connectedToMasterstage, false)
    fun setConnectedToMasterstage(c: Context?, connected: Boolean) = pref(c).edit().putBoolean(connectedToMasterstage, connected).apply()

    fun getClasse(c: Context) = pref(c).getString(classe, "")
    fun setClasse(c: Context, cl: String) = pref(c).edit().putString(classe, cl).apply()

    private fun pref(c: Context?) = PreferenceManager.getDefaultSharedPreferences(c
            ?: throw NullPointerException("Context must not be null"))
}
