package com.uas.practica06

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ListaScreen() {
    // Obtener contactos
    val contactos = obtenerContactosDummy()

    // LazyColumn equivalente a RecyclerView
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado automatico
    ) {
        // funcion items() itera sobre coleccion de datos
        items(contactos) {
            contacto -> ContactoItem(contacto = contacto)
        }
    }
}

@Composable
fun ContactoItem(contacto: Contacto) {
    // Contexto pal toast
    val context = LocalContext.current
    // Card para datos
    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE3F2FD)).padding(16.dp)
                .clickable {
                    Toast.makeText(context, "Seleccionaste a ${contacto.nombre}", Toast.LENGTH_SHORT).show()
                }
        ) {
            Text(
                text = contacto.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tel: ${contacto.telefono}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}