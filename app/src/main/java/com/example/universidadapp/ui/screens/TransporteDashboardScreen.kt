package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TransporteDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Transporte - Menú") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Pantalla principal del personal de transporte.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Acceso al escáner de QR y al historial de viajes.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
