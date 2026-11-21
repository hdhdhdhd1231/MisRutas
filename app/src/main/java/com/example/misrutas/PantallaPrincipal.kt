package com.example.misrutas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    visitas: List<Visita>,
    busqueda: String,
    categoriaFiltro: String?,
    snackbarHostState: SnackbarHostState,
    alCambiarBusqueda: (String) -> Unit,
    alCambiarFiltro: (String?) -> Unit,
    alPulsarVisita: (Visita) -> Unit,
    alPulsarAnadir: () -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MisRutas",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Box {
                        IconButton(onClick = { menuAbierto = true }) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "Filtrar por categoría"
                            )
                        }
                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = { menuAbierto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Todas") },
                                leadingIcon = { if (categoriaFiltro == null) Icon(Icons.Default.Check, contentDescription = null) },
                                onClick = {
                                    menuAbierto = false
                                    alCambiarFiltro(null)
                                }
                            )
                            for (categoria in categorias) {
                                DropdownMenuItem(
                                    text = { Text(categoria) },
                                    leadingIcon = { if (categoriaFiltro == categoria) Icon(Icons.Default.Check, contentDescription = null) },
                                    onClick = {
                                        menuAbierto = false
                                        alCambiarFiltro(categoria)
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = alPulsarAnadir) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir visita"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = alCambiarBusqueda,
                label = { Text("Buscar por nombre") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Text(
                text = "Mis visitas · ${categoriaFiltro ?: "Todas"}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            if (visitas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay visitas. Pulsa '+' para añadir una.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 80.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = visitas,
                        key = { visita -> visita.id }
                    ) { visita ->
                        TarjetaVisita(
                            visita = visita,
                            alPulsar = { alPulsarVisita(visita) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaVisita(
    visita: Visita,
    alPulsar: () -> Unit
) {
    ElevatedCard(
        onClick = alPulsar,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconoCategoria(categoria = visita.categoria)

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = visita.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${visita.fecha} · ${visita.categoria}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Estrellas(valoracion = visita.valoracion)
            }
        }
    }
}