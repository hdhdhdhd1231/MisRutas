package com.example.misrutas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val EsquemaClaro = lightColorScheme(
    primary = VerdeBosque,
    onPrimary = Blanco,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = VerdeOscuro,
    background = Blanco,
    onBackground = VerdeOscuro,
    surface = Blanco,
    onSurface = VerdeOscuro,
    onSurfaceVariant = VerdeOscuro,
    surfaceContainerLowest = Blanco,
    surfaceContainerLow = Blanco,
    surfaceContainer = Blanco,
    surfaceContainerHigh = Blanco,
    surfaceContainerHighest = Blanco,
    outline = VerdeBosque,
    outlineVariant = VerdeClaro
)

@Composable
fun MisRutasTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        typography = Typography,
        content = content
    )
}