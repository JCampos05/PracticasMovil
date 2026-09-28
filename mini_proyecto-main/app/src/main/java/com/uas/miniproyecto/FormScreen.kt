package com.uas.miniproyecto

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uas.miniproyecto.activity.ProfileActivity
import com.uas.miniproyecto.components.CustomRadioButton
import com.uas.miniproyecto.components.CustomSpinner
import com.uas.miniproyecto.components.CustomSwitch
import com.uas.miniproyecto.data.PreferencesManager

@Composable
fun FormScreen() {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var studentId by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var shift by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(true) }
    var darkTheme by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        studentId = preferencesManager.getStudentId()
        fullName = preferencesManager.getFullName()
        degree = preferencesManager.getDegree().ifBlank { "Seleccionar opcion" }
        shift = preferencesManager.getShift()
        status = preferencesManager.getStatus()
        darkTheme = preferencesManager.getDarkThemeFlag()
    }

    val colorScheme = if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(colorScheme = colorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),

            ) {
                Text(
                    text = "Mini proyecto 1 - Formulario de Registro",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )

                HorizontalDivider()

                // Matricula
                OutlinedTextField(
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = { Text("Matricula") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Nombre
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Nombre Completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider()

                // Carrera

                Text("Carrera:", style = MaterialTheme.typography.titleMedium)

                CustomSpinner(
                    selectedText = degree,
                    onOptionSelected = { degree = it }
                )

                // Turno
                Text("Turno:", style = MaterialTheme.typography.titleMedium)

                CustomRadioButton(
                    selectedOption = shift,
                    onOptionSelected = { shift = it }
                )

                HorizontalDivider()

                // Estado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomSwitch(
                        checked = status,
                        onCheckedChange = { status = it }
                    )
                }

                HorizontalDivider()

                // Guardado & Intent
                Button(
                    onClick = {

                        if (studentId.isBlank() || fullName.isBlank() || degree == "Seleccionar opcion") {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        preferencesManager.saveSettings(
                            student_id = studentId,
                            full_name = fullName,
                            degree = degree,
                            shift = shift,
                            status = status
                        )

                        Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()

                        val intent = Intent(context, ProfileActivity::class.java).apply() {
                            putExtra("EXTRA_STUDENT_ID", studentId)
                            putExtra("EXTRA_FULL_NAME", fullName)
                            putExtra("EXTRA_DEGREE", degree)
                            putExtra("EXTRA_SHIFT", shift)
                            putExtra("EXTRA_STATUS", if (status) "Activo" else "Inactivo")
                        }

                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar estudiante")
                }

                // Cargar
                OutlinedButton(
                    onClick = {
                        studentId = preferencesManager.getStudentId()
                        fullName = preferencesManager.getFullName()
                        degree = preferencesManager.getDegree()
                        shift = preferencesManager.getShift()
                        status = preferencesManager.getStatus()

                        Toast.makeText(context, "Datos cargados", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cargar datos guardados")
                }

                // Limpiar
                TextButton(
                    onClick = {
                        preferencesManager.clearPreferences()
                        studentId = ""
                        fullName = ""
                        degree = "Seleccionar opcion"
                        shift = ""
                        status = false

                        Toast.makeText(context, "Datos eliminados", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Limpiar datos", color = MaterialTheme.colorScheme.error)
                }

                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Activar Tema Oscuro")
                    Switch(
                        checked = darkTheme,
                        onCheckedChange = { darkTheme = it }
                    )
                }
            }
        }
    }
}