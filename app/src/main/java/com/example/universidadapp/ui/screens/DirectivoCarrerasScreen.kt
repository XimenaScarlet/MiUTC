package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DirectivoCarrerasScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Directivo - Carreras") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Catálogo de carreras de la UTC separadas en TCU y Ingeniería.")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Se usa para asignar alumnos a programas oficiales.")
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
