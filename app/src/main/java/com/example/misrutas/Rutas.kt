package com.example.misrutas


object Rutas {
    const val LISTA = "lista"
    const val DETALLE = "detalle/{visitaId}"

    fun detalle(visitaId: String) = "detalle/$visitaId"
}