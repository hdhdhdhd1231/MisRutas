package com.example.misrutas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Estrellas(
    valoracion: Int,
    tamano: Dp = 18.dp,
    colorVacia: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (i <= valoracion) MaterialTheme.colorScheme.primary else colorVacia,
                modifier = Modifier.size(tamano)
            )
        }
    }
}

@Composable
fun IconoCategoria(
    categoria: String,
    tamano: Dp = 56.dp,
    tamanoDibujo: Int = 28
) {
    val dibujo = when (categoria) {
        "Montaña" -> "⛰️"
        "Playa" -> "🏖️"
        else -> "🏙️"
    }

    Box(
        modifier = Modifier
            .size(tamano)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dibujo,
            fontSize = tamanoDibujo.sp
        )
    }
}