package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MaestroPerfilScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Maestro - Perfil") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Datos personales del maestro, departamento y materias que imparte.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Opcionalmente se puede mostrar un Código QR institucional para usos internos.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
