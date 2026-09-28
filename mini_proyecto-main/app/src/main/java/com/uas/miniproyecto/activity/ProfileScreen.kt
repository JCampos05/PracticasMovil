package com.uas.miniproyecto.activity

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    studentId: String,
    fullName: String,
    degree: String,
    shift: String,
    status: String,
    onBackClick: () -> Unit
) {
    val  context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Perfil del Estudiante",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "Matricula:", style = MaterialTheme.typography.labelLarge)
                    Text(text = studentId, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Nombre:", style = MaterialTheme.typography.labelLarge)
                    Text(text = fullName, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Carrera:", style = MaterialTheme.typography.labelLarge)
                    Text(text = degree, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Turno:", style = MaterialTheme.typography.labelLarge)
                    Text(text = shift, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Estado:", style = MaterialTheme.typography.labelLarge)
                    Text(text = status, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }

            OutlinedButton(
                onClick = {
                    // Intent implicito
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, "" +
                                "Datos del estudiante:\n" +
                                "Matricula: $studentId\n" +
                                "Nombre: $fullName\n" +
                                "Carrera: $degree\n" +
                                "Turno: $shift\n" +
                                "Estado: $status\n")

                        type = "text/plain"
                    }

                    val chooser = Intent.createChooser(sendIntent, "Compartir datos usando:")

                    context.startActivity(chooser)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Compartir datos del Estudiante")
            }
        }
    }
}