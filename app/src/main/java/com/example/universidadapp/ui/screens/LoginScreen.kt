package com.example.universidadapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLoginAsAlumno: () -> Unit,
    onLoginAsMaestro: () -> Unit,
    onLoginAsDirectivo: () -> Unit,
    onLoginAsTransporte: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Red Universitaria UTC", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo / Matrícula") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Por ahora el login es simulado por rol. Después se conecta a la API.",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLoginAsAlumno, modifier = Modifier.fillMaxWidth()) {
            Text("Entrar como Alumno (demo)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onLoginAsMaestro, modifier = Modifier.fillMaxWidth()) {
            Text("Entrar como Maestro (demo)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onLoginAsDirectivo, modifier = Modifier.fillMaxWidth()) {
            Text("Entrar como Directivo (demo)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onLoginAsTransporte, modifier = Modifier.fillMaxWidth()) {
            Text("Entrar como Transporte (demo)")
        }
    }
}
