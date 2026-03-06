package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoMateriasScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Materias") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Lista de materias inscritas del alumno.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Cada materia tendrá acceso al detalle con tareas, calificaciones, material, anuncios y asistencia.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
