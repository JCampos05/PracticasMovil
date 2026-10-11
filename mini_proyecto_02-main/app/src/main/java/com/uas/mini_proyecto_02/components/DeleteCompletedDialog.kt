package com.uas.mini_proyecto_02.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteCompletedDialog(
    completedCount: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Borrar completadas") },
        text = { Text("Se eliminaran $completedCount tarea(s) completada(s). ¿Continuar?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Borrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}