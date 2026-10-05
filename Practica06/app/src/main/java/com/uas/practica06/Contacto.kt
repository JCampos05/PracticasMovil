package com.uas.practica06

data class Contacto(
    val id: Int,
    val nombre: String,
    val telefono: String
)

fun obtenerContactosDummy(): List<Contacto> {
    return listOf(
        Contacto(1, "Juancho", "555-0123"),
        Contacto(2, "Isma", "555-3210"),
        Contacto(3, "Sañusable", "555-1234"),
        Contacto(4, "Trinomio", "123-4567"),
        Contacto(5, "Alan", "999-1234"),
        Contacto(6, "Negro", "888-9900"),
        Contacto(7, "Orrantia", "444-5566"),
        Contacto(8, "Sonic (Arturo)", "777-1234"),
        Contacto(9, "Rosa", "0180-0838"),
        Contacto(10, "Mencho", "999-1234"),
        Contacto(11, "Piratita", "123-4321"),
        Contacto(12, "Madueña", "300-0000")
    )
}