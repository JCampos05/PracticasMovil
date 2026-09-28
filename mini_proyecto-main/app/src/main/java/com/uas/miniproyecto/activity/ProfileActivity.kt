package com.uas.miniproyecto.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Valores
        val studentId = intent.getStringExtra("EXTRA_STUDENT_ID") ?: "Sin matricula"
        val fullName = intent.getStringExtra("EXTRA_FULL_NAME") ?: "Sin nombre"
        val degree = intent.getStringExtra("EXTRA_DEGREE") ?: "Sin carrera"
        val shift = intent.getStringExtra("EXTRA_SHIFT") ?: "Sin turno"
        val status = intent.getStringExtra("EXTRA_STATUS") ?: "Sin estado"

        setContent {
            ProfileScreen(
                studentId,
                fullName,
                degree,
                shift,
                status,
                onBackClick = { finish() }
            )
        }
    }
}