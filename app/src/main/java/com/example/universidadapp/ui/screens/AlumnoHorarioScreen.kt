package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoHorarioScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Horario") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Aquí se mostrará el horario semanal (Lunes a Viernes) por hora.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Cada bloque mostrará materia, profesor y aula, con accesos al detalle de la materia.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
