package com.simposio.practica13

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BurbujaScreen(sensorManager: SensorManager) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val sensitivy = 50f

    val maxRadius = 250f

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // No se requiere acción
            }

            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val rawX = -it.values[0] * sensitivy
                    val rawY = it.values[1] * sensitivy

                    val distance = sqrt((rawX * rawX) + (rawY * rawY))

                    if (distance > maxRadius) {
                        val proportion = maxRadius / distance
                        offsetX = rawX * proportion
                        offsetY = rawY * proportion
                    } else {
                        offsetX = rawX
                        offsetY = rawY
                    }
                }
            }
        }

        val acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(listener, acelerometer, SensorManager.SENSOR_DELAY_GAME)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nivel de Burbuja") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(50.dp).border(2.dp, Color.Red, CircleShape))

            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt())}
                    .size(40.dp)
                    .background(Color(0xFF00FF00), CircleShape)
            )

            Column(
                modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Eje X: ${"%.2f".format(offsetX / sensitivy)}", color = Color.White, fontSize = 20.sp)
                Text("Eje Y: ${"%.2f".format(offsetY / sensitivy)}", color = Color.White, fontSize = 20.sp)
            }
        }
    }


}