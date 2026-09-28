package com.uas.practica05

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uas.practica05.data.PreferencesManager

@Composable
fun FormScreen() {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    // States
    var username by remember { mutableStateOf("") }
    var notificationsEnabled by remember { mutableStateOf(false) }
    var darkThemeEnabled by remember { mutableStateOf(false) }

    // Load data
    LaunchedEffect(Unit) {
        username = preferencesManager.getUsername()
        notificationsEnabled = preferencesManager.getNotifications()
        darkThemeEnabled = preferencesManager.getDarkTheme()
    }

    // Switch color palette
    val colorScheme = if (darkThemeEnabled) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    // Material Theme
    MaterialTheme(colorScheme = colorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Practica 5: Configuracion",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )

                HorizontalDivider()

                // User text
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nombre de Usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Notifications Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Recibir Notificaciones:", fontSize = 16.sp)
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Activar Tema Oscuro")
                    Switch(
                        checked = darkThemeEnabled,
                        onCheckedChange = { darkThemeEnabled = it }
                    )
                }

                HorizontalDivider()

                // Save preferences
                Button(
                    onClick = {
                        preferencesManager.saveSettings(
                            username = username,
                            notifications = notificationsEnabled,
                            darkTheme = darkThemeEnabled
                        )

                        Toast.makeText(context, "Configuracion guardada", Toast.LENGTH_SHORT).show()
                    },

                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Preferencias")
                }

                // Reload/Recover
                OutlinedButton(
                    onClick = {
                        username = preferencesManager.getUsername()
                        notificationsEnabled = preferencesManager.getNotifications()
                        darkThemeEnabled = preferencesManager.getDarkTheme()

                        Toast.makeText(context, "Preferencias Cargadas", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Recargar Datos Guardados")
                }

                // Clear
                TextButton(
                    onClick = {
                        preferencesManager.clearPreferences()
                        username = ""
                        notificationsEnabled = false
                        darkThemeEnabled = false

                        Toast.makeText(context, "Preferencias eliminadas", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Restablecer Configuracion", color = MaterialTheme.colorScheme.error)
                }

            }
        }
    }
}