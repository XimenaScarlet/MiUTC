package com.example.univapp.ui.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univapp.data.Carrera
import com.example.univapp.data.Grupo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGruposScreen(
    onBack: () -> Unit,
    onAddManually: () -> Unit,
    onImportExcel: () -> Unit,
    uiState: AdminGruposUiState,
    onCarreraSelected: (Carrera?) -> Unit,
    onGroupClick: (String) -> Unit // <-- CAMBIO AQUÍ
) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF5F6F8),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Grupos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            if (uiState.selectedCarrera != null) {
                Box {
                    FloatingActionButton(
                        onClick = { showMenu = true },
                        containerColor = Color(0xFF673AB7),
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Agregar Grupo")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(text = { Text("Agregar Manualmente") }, onClick = {
                            onAddManually()
                            showMenu = false
                        })
                        DropdownMenuItem(text = { Text("Importar Excel") }, onClick = {
                            onImportExcel()
                            showMenu = false
                        })
                    }
                }
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(horizontal = 16.dp)) {
            CarreraSelectorGrupos(
                carreras = uiState.carreras,
                selectedCarrera = uiState.selectedCarrera,
                onCarreraSelected = onCarreraSelected
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading && uiState.grupos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.selectedCarrera == null) {
                EmptyState("Selecciona una carrera para ver los grupos.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (uiState.grupos.isEmpty()) {
                        item { EmptyState(message = "No hay grupos en esta carrera.") }
                    } else {
                        items(uiState.grupos, key = { it.id }) { grupo ->
                            GrupoItem(grupo = grupo, onClick = { onGroupClick(grupo.id) }) // <-- CAMBIO AQUÍ
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CarreraSelectorGrupos(
    carreras: List<Carrera>,
    selectedCarrera: Carrera?,
    onCarreraSelected: (Carrera?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier) {
        OutlinedTextField(
            value = selectedCarrera?.nombre ?: "Selecciona una carrera",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            leadingIcon = { Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFF007AFF)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray.copy(alpha = 0.5f),
                focusedIndicatorColor = Color(0xFF007AFF)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text("Todas") },
                onClick = {
                    onCarreraSelected(null)
                    expanded = false
                }
            )
            carreras.forEach { carrera ->
                DropdownMenuItem(
                    text = { Text(carrera.nombre ?: "") },
                    onClick = {
                        onCarreraSelected(carrera)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun GrupoItem(grupo: Grupo, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(grupo.nombre ?: "", fontWeight = FontWeight.Bold)
                Text("${grupo.numAlumnos ?: 0} Alumnos", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text(grupo.turno ?: "", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Default.School, contentDescription = null, tint = Color.LightGray.copy(alpha = 0.5f), modifier = Modifier.size(100.dp))
            Text(message, fontSize = 18.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}
