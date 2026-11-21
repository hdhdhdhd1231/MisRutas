package com.example.misrutas


object VisitaRepository {
    private val visitas = mutableListOf(
        Visita(
            nombre = "Mirador del Cabo",
            fecha = "12/10/2026",
            categoria = "Playa",
            valoracion = 5,
            notas = "Subida corta desde el aparcamiento. Fuimos al atardecer y las vistas eran increíbles. Llevar agua."
        ),
        Visita(
            nombre = "Ruta Sierra Norte",
            fecha = "24/03/2024",
            categoria = "Montaña",
            valoracion = 1,
            notas = "Ruta muy larga y mal señalizada. Hubo niebla todo el día."
        ),
        Visita(
            nombre = "Casco antiguo",
            fecha = "01/09/2026",
            categoria = "Ciudad",
            valoracion = 3,
            notas = "Calles bonitas y muchas terrazas, pero demasiada gente a mediodía."
        )
    )

    fun obtenerVisitas(): List<Visita> = visitas.toList()

    fun obtenerVisitaPorId(id: String): Visita? = visitas.find { it.id == id }

    fun agregarVisita(visita: Visita) {
        visitas.add(visita)
    }

    fun actualizarVisita(visita: Visita) {
        val posicion = visitas.indexOfFirst { it.id == visita.id }
        if (posicion != -1) {
            visitas[posicion] = visita
        }
    }

    fun borrarVisita(id: String) {
        visitas.removeAll { it.id == id }
    }
}