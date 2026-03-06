package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoTareasScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Tareas") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Listado de todas las tareas de todas las materias.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Aquí se verá el estado (pendiente, entregada, retrasada, calificada) y se podrá entrar al detalle.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
