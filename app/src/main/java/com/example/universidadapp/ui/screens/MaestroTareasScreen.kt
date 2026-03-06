package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MaestroTareasScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Maestro - Tareas") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Listado de tareas creadas por el maestro.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Cada tarea muestra entregas de alumnos, adjuntos (PDF) y chat privado por tarea.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
