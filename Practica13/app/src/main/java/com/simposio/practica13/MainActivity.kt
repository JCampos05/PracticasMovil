package com.simposio.practica13

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.simposio.practica13.ui.theme.Practica13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica13Theme {
                BurbujaScreen(sensorManager = sensorManager)
            }
        }
    }
}

