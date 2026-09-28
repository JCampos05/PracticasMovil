package com.uas.miniproyecto.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences",
        Context.MODE_PRIVATE)

    companion object {
        const val KEY_STUDENT_ID = "key_student_id"
        const val KEY_FULL_NAME = "key_full_name"
        const val KEY_DEGREE = "key_degree"
        const val KEY_SHIFT = "key_shift"
        const val KEY_STATUS = "key_status"
        const val KEY_DARK_THEME = "key_dark_theme"
    }

    fun saveSettings(student_id: String, full_name: String, degree: String, shift: String, status: Boolean) {
        val editor = sharedPreferences.edit()

        editor.putString(KEY_STUDENT_ID, student_id)
        editor.putString(KEY_FULL_NAME, full_name)
        editor.putString(KEY_DEGREE, degree)
        editor.putString(KEY_SHIFT, shift)
        editor.putBoolean(KEY_STATUS, status)
        editor.apply()
    }

    fun saveSettings(darkTheme: Boolean) {
        val editor = sharedPreferences.edit()

        editor.putBoolean(KEY_DARK_THEME, darkTheme)
    }

    fun getStudentId(): String {
        return sharedPreferences.getString(KEY_STUDENT_ID, "") ?: ""
    }

    fun getFullName(): String {
        return sharedPreferences.getString(KEY_FULL_NAME, "") ?: ""
    }

    fun getDegree(): String {
        return sharedPreferences.getString(KEY_DEGREE, "") ?: ""
    }

    fun getShift(): String {
        return sharedPreferences.getString(KEY_SHIFT, "") ?: ""
    }

    fun getStatus(): Boolean {
        return sharedPreferences.getBoolean(KEY_STATUS, false)
    }

    fun getDarkThemeFlag(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_THEME, false)
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}