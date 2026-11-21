package com.example.misrutas

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppPantallas() {
    val navController = rememberNavController()

    var visitas by remember { mutableStateOf(VisitaRepository.obtenerVisitas()) }
    var busqueda by rememberSaveable { mutableStateOf("") }
    var categoriaFiltro by rememberSaveable { mutableStateOf<String?>(null) }
    var dialogoAltaAbierto by rememberSaveable { mutableStateOf(false) }
    var visitaAEditar by remember { mutableStateOf<Visita?>(null) }
    var visitaABorrar by remember { mutableStateOf<Visita?>(null) }
    var mensaje by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    val visitasFiltradas = visitas.filter { visita ->
        visita.nombre.uppercase().contains(busqueda.trim().uppercase()) && (categoriaFiltro == null || visita.categoria == categoriaFiltro)
    }

    LaunchedEffect(mensaje) {
        mensaje?.let { texto ->
            snackbarHostState.showSnackbar(texto)
            mensaje = null
        }
    }

    NavHost(
        navController = navController,
        startDestination = Rutas.LISTA,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) }
    ) {
        composable(Rutas.LISTA) {
            PantallaPrincipal(
                visitas = visitasFiltradas,
                busqueda = busqueda,
                categoriaFiltro = categoriaFiltro,
                snackbarHostState = snackbarHostState,
                alCambiarBusqueda = { busqueda = it },
                alCambiarFiltro = { categoriaFiltro = it },
                alPulsarVisita = { visita -> navController.navigate(Rutas.detalle(visita.id)) },
                alPulsarAnadir = { dialogoAltaAbierto = true }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("visitaId") { type = NavType.StringType })
        ) { entrada ->
            val visitaId = entrada.arguments?.getString("visitaId")
            val visita = visitas.find { it.id == visitaId }
            if (visita != null) {
                PantallaDetalle(
                    visita = visita,
                    snackbarHostState = snackbarHostState,
                    alVolver = { navController.popBackStack() },
                    alEditar = { visitaAEditar = visita },
                    alBorrar = { visitaABorrar = visita }
                )
            }
        }
    }

    if (dialogoAltaAbierto) {
        FormularioVisitaDialogo(
            alCancelar = { dialogoAltaAbierto = false },
            alGuardar = { nombre, fecha, categoria, valoracion, notas ->
                val nueva = Visita(
                    nombre = nombre,
                    fecha = fecha,
                    categoria = categoria,
                    valoracion = valoracion,
                    notas = notas
                )
                VisitaRepository.agregarVisita(nueva)
                visitas = VisitaRepository.obtenerVisitas()
                dialogoAltaAbierto = false
                mensaje = "Visita '${nueva.nombre}' añadida"
            }
        )
    }

    visitaAEditar?.let { visita ->
        FormularioVisitaDialogo(
            visitaAEditar = visita,
            alCancelar = { visitaAEditar = null },
            alGuardar = { nombre, fecha, categoria, valoracion, notas ->
                val actualizada = visita.copy(
                    nombre = nombre,
                    fecha = fecha,
                    categoria = categoria,
                    valoracion = valoracion,
                    notas = notas
                )
                VisitaRepository.actualizarVisita(actualizada)
                visitas = VisitaRepository.obtenerVisitas()
                visitaAEditar = null
                mensaje = "Visita actualizada"
            }
        )
    }

    visitaABorrar?.let { visita ->
        ConfirmarBorradoDialogo(
            visita = visita,
            alCancelar = { visitaABorrar = null },
            alConfirmar = {
                VisitaRepository.borrarVisita(visita.id)
                visitas = VisitaRepository.obtenerVisitas()
                visitaABorrar = null
                mensaje = "Visita '${visita.nombre}' eliminada"
                navController.popBackStack()
            }
        )
    }
}