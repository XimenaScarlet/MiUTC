package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoPerfilScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Perfil") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Datos personales y académicos del alumno (matrícula, carrera, grupo, tipo de programa).")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Sección para mostrar el Código QR institucional y opciones de descarga/visualización.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
