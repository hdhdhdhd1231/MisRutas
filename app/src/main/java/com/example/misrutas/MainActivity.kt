package com.example.misrutas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.misrutas.ui.theme.MisRutasTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisRutasTheme {
                AppPantallas()
            }
        }
    }
}

@Composable
fun AppPantallas() {
    var idSeleccionado by rememberSaveable { mutableIntStateOf(0) }

    if (idSeleccionado == 0) {
        PantallaLista(
            alPulsarVisita = { idSeleccionado = it.id },
        )
    } else {
        PantallaDetalle(
            visita = visitas.first { it.id == idSeleccionado },
            alVolver = { idSeleccionado = 0 },
        )
    }
}

data class Visita(
    val id: Int,
    val nombre: String,
    val fecha: String,
    val categoria: String,
    val valoracion: Int,
    val notas: String,
)

val visitas = listOf(
    Visita(
        id = 1,
        nombre = "Mirador del Cabo",
        fecha = "12/10/2026",
        categoria = "Playa",
        valoracion = 5,
        notas = "Subida corta desde el aparcamiento. Fuimos al atardecer y las vistas eran increíbles. Llevar agua.",
    ),
    Visita(
        id = 2,
        nombre = "Ruta Sierra Norte",
        fecha = "24/03/2024",
        categoria = "Montaña",
        valoracion = 1,
        notas = "Ruta muy larga y mal señalizada. Hubo niebla todo el día.",
    ),
    Visita(
        id = 3,
        nombre = "Casco antiguo",
        fecha = "01/09/2026",
        categoria = "Ciudad",
        valoracion = 3,
        notas = "Calles bonitas y muchas terrazas, pero demasiada gente a mediodía.",
    ),
)

fun estrellas(valoracion: Int): String = "★".repeat(valoracion) + "☆".repeat(5 - valoracion)

@Preview(showBackground = true)
@Composable
fun VistaPreviaApp() {
    MisRutasTheme {
        AppPantallas()
    }
}