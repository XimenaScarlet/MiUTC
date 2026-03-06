package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DirectivoGruposScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Directivo - Grupos") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Creación y administración de grupos:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Asignar carrera, periodo, turno, materias y maestros; configurar horarios.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
