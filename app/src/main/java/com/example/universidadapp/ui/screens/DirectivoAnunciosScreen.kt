package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DirectivoAnunciosScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Directivo - Anuncios") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Creación de anuncios globales o segmentados:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Por tipo de programa (TCU / Ingeniería), por carrera o por grupo.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
