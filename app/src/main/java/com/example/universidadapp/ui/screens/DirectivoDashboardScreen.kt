package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DirectivoDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Directivo - Dashboard") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Vista general de la universidad:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Totales de alumnos, maestros y grupos; estadísticas de asistencia y reprobación.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Acceso rápido a gestión de alumnos, maestros, carreras, grupos, anuncios y transporte.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
