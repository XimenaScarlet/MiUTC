package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoAnunciosScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Anuncios") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Listado de anuncios que aplican al alumno:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Globales, por carrera, por tipo de programa (TCU / Ingeniería), por grupo y por materia.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
