package com.example.misrutas


data class Visita(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nombre: String,
    val fecha: String,
    val categoria: String,
    val valoracion: Int,
    val notas: String
)

val categorias = listOf("Montaña", "Playa", "Ciudad")