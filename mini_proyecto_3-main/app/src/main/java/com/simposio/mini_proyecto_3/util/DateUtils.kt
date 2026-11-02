package com.simposio.mini_proyecto_3.util

import android.icu.util.Calendar
import android.icu.util.TimeZone
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun format(millis: Long): String =
        SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-MX")).format(Date(millis))

    fun localToUtc(millis: Long): Long {
        val local = Calendar.getInstance().apply { timeInMillis = millis }
        return Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            clear()
            set(local.get(Calendar.YEAR), local.get(Calendar.MONTH), local.get(Calendar.DAY_OF_MONTH))
        }.timeInMillis
    }

    fun utcToLocal(utcMillis: Long): Long {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply { timeInMillis = utcMillis }
        return Calendar.getInstance().apply {
            clear()
            set(utc.get(Calendar.YEAR), utc.get(Calendar.MONTH), utc.get(Calendar.DAY_OF_MONTH))
        }.timeInMillis
    }
}