package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MaestroGruposScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Maestro - Grupos") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Lista de grupos donde el maestro imparte clase.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Desde aquí se gestionan alumnos, asistencias, calificaciones, tareas y anuncios por grupo.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
