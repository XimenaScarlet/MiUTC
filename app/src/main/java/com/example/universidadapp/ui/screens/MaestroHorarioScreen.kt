package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MaestroHorarioScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Maestro - Horario") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Horario semanal del maestro (Lunes a Viernes) con materias, grupos y aulas.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Cada bloque permite acceder al detalle del grupo.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
