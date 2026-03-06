package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MaestroDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Maestro - Dashboard") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Resumen general del maestro:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("- Clases del día")
            Spacer(modifier = Modifier.height(4.dp))
            Text("- Tareas por revisar")
            Spacer(modifier = Modifier.height(4.dp))
            Text("- Accesos a grupos, asistencias, calificaciones, anuncios y perfil.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
