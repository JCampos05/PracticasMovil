package com.simposio.mini_proyecto_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.simposio.mini_proyecto_3.presentation.CreateMainScreen
import com.simposio.mini_proyecto_3.ui.theme.Mini_proyecto_3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mini_proyecto_3Theme {
                CreateMainScreen()
            }
        }
    }
}