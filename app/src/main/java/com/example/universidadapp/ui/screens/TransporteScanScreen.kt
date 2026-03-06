package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TransporteScanScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Transporte - Escanear QR") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Aquí se integrará la cámara para leer el Código QR del alumno.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Al escanear, se llamará a la API para validar al alumno y registrar el viaje.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
