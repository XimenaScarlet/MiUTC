package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Dashboard") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Resumen general del día del alumno:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("- Clases de hoy")
            Spacer(modifier = Modifier.height(4.dp))
            Text("- Tareas próximas")
            Spacer(modifier = Modifier.height(4.dp))
            Text("- Anuncios recientes")
            Spacer(modifier = Modifier.height(4.dp))
            Text("- Accesos a Horario, Materias, Tareas, Calificaciones, Transporte, Perfil (con QR), Configuración")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
