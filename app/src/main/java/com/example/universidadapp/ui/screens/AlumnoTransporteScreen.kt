package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlumnoTransporteScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alumno - Transporte") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Aquí el alumno verá su Código QR institucional para el transporte.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("También podrá consultar historial de viajes e información básica de rutas.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
