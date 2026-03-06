package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoCalificacionesScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Calificaciones") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Calificaciones por materia y promedio general del alumno.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Puedes agregar gráficas de desempeño por periodo.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
