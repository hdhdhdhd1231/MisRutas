package com.example.misrutas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.misrutas.ui.theme.MisRutasTheme


@Composable
fun PantallaDetalle(
    visita: Visita,
    alVolver: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "📷",
                style = MaterialTheme.typography.displayMedium,
            )
        }

        Text(
            text = visita.nombre,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = estrellas(visita.valoracion),
            style = MaterialTheme.typography.titleLarge,
        )
        Text("Fecha: ${visita.fecha}")
        Text("Categoría: ${visita.categoria}")
        Text("Notas: ${visita.notas}")

        Text("Ubicación:")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "📍",
                style = MaterialTheme.typography.displaySmall,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = alVolver,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Volver a la lista")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaDetalle() {
    MisRutasTheme {
        PantallaDetalle(
            visita = visitas[0],
            alVolver = {},
        )
    }
}