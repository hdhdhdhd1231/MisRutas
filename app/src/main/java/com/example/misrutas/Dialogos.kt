package com.example.misrutas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ConfirmarBorradoDialogo(
    visita: Visita,
    alCancelar: () -> Unit,
    alConfirmar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = alCancelar,
        title = { Text("Eliminar visita") },
        text = { Text("¿Seguro que quieres eliminar '${visita.nombre}'?") },
        confirmButton = {
            TextButton(
                onClick = alConfirmar,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = alCancelar) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun CampoFormulario(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    error: String? = null,
    unaLinea: Boolean = true
) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        singleLine = unaLinea,
        isError = error != null,
        supportingText = if (error != null) { { Text(error) } } else null,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SelectorCategoria(
    categoria: String,
    alCambiar: (String) -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            onClick = { menuAbierto = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            IconoCategoria(
                categoria = categoria,
                tamano = 28.dp,
                tamanoDibujo = 14
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Categoría: $categoria")
        }
        DropdownMenu(
            expanded = menuAbierto,
            onDismissRequest = { menuAbierto = false }
        ) {
            for (opcion in categorias) {
                DropdownMenuItem(
                    text = { Text(opcion) },
                    leadingIcon = {
                        IconoCategoria(
                            categoria = opcion,
                            tamano = 28.dp,
                            tamanoDibujo = 14
                        )
                    },
                    onClick = {
                        menuAbierto = false
                        alCambiar(opcion)
                    }
                )
            }
        }
    }
}

@Composable
fun SelectorEstrellas(
    valoracion: Int,
    alCambiar: (Int) -> Unit
) {
    Column {
        Text(
            text = "Valoración",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            for (i in 1..5) {
                IconButton(onClick = { alCambiar(i) }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "$i estrellas",
                        tint = if (i <= valoracion) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun FormularioVisitaDialogo(
    visitaAEditar: Visita? = null,
    alCancelar: () -> Unit,
    alGuardar: (nombre: String, fecha: String, categoria: String, valoracion: Int, notas: String) -> Unit
) {
    var nombre by remember { mutableStateOf(visitaAEditar?.nombre ?: "") }
    var fecha by remember { mutableStateOf(visitaAEditar?.fecha ?: "") }
    var categoria by remember { mutableStateOf(visitaAEditar?.categoria ?: categorias[0]) }
    var valoracion by remember { mutableIntStateOf(visitaAEditar?.valoracion ?: 3) }
    var notas by remember { mutableStateOf(visitaAEditar?.notas ?: "") }

    var mostrarErrores by remember { mutableStateOf(false) }

    val errorNombre = if (nombre.isBlank()) "El nombre es obligatorio" else null
    val errorFecha = when {
        fecha.isBlank() -> "La fecha es obligatoria"
        fecha.length != 10 || fecha[2] != '/' || fecha[5] != '/' || fecha.count { it.isDigit() } != 8 -> "Usa el formato dd/mm/aaaa"
        else -> null
    }

    val formularioValido = errorNombre == null && errorFecha == null

    AlertDialog(
        onDismissRequest = alCancelar,
        title = { Text(if (visitaAEditar == null) "Nueva visita" else "Editar visita") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CampoFormulario(
                    valor = nombre,
                    alCambiar = { nombre = it },
                    etiqueta = "Nombre del lugar *",
                    error = if (mostrarErrores) errorNombre else null
                )
                CampoFormulario(
                    valor = fecha,
                    alCambiar = { fecha = it },
                    etiqueta = "Fecha (dd/mm/aaaa) *",
                    error = if (mostrarErrores) errorFecha else null
                )
                SelectorCategoria(
                    categoria = categoria,
                    alCambiar = { categoria = it }
                )
                SelectorEstrellas(
                    valoracion = valoracion,
                    alCambiar = { valoracion = it }
                )
                CampoFormulario(
                    valor = notas,
                    alCambiar = { notas = it },
                    etiqueta = "Notas",
                    unaLinea = false
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (formularioValido) {
                        alGuardar(nombre.trim(), fecha.trim(), categoria, valoracion, notas.trim())
                    } else {
                        mostrarErrores = true
                    }
                }
            ) {
                Text(if (visitaAEditar == null) "Guardar" else "Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = alCancelar) {
                Text("Cancelar")
            }
        }
    )
}