package com.uas.mini_proyecto_02.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    companion object {
        const val KEY_DARK_THEME = "key_dark_theme"
    }

    fun saveDarkTheme(darkTheme: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_DARK_THEME, darkTheme)
        editor.apply()
    }

    fun getDarkTheme(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_THEME, false)
    }
}