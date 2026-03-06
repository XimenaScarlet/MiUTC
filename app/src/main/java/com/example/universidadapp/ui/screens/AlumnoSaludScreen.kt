package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoSaludScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Salud") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Información del servicio médico de la universidad:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Horarios, ubicación, teléfonos y recursos de salud física y mental.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
