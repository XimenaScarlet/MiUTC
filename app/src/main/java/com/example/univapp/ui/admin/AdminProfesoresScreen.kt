package com.example.univapp.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univapp.data.Carrera
import com.example.univapp.data.Profesor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProfesoresScreen(
    onBack: () -> Unit,
    onAddManually: () -> Unit,
    onImportExcel: () -> Unit,
    vm: AdminProfesoresViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profesores", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Atrás") } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            if (uiState.selectedCarrera != null) {
                Box {
                    FloatingActionButton(
                        onClick = { showMenu = true },
                        containerColor = Color(0xFF6200EE),
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, "Añadir Profesor")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(text = { Text("Agregar Manualmente") }, onClick = { showMenu = false; onAddManually() })
                        DropdownMenuItem(text = { Text("Importar Excel") }, onClick = { showMenu = false; onImportExcel() })
                    }
                }
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("CARRERA", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            CarreraSelectorProfesores(uiState.carreras, uiState.selectedCarrera, { vm.onCarreraSelected(it) })
            Spacer(Modifier.height(24.dp))

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            } else if (uiState.selectedCarrera == null) {
                EmptyStateProfesores()
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (uiState.profesores.isEmpty()) {
                        item {
                            Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No hay profesores en esta carrera.", textAlign = TextAlign.Center)
                            }
                        }
                    } else {
                        items(uiState.profesores, key = { it.id }) { profesor ->
                            ProfesorListItem(profesor = profesor, onClick = { /* TODO */ })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CarreraSelectorProfesores(
    carreras: List<Carrera>,
    selectedCarrera: Carrera?,
    onCarreraSelected: (Carrera?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedCarrera?.nombre ?: "Selecciona una carrera",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            leadingIcon = {
                Box(modifier = Modifier.size(40.dp).background(Color(0xFFF0F4FF), RoundedCornerShape(8.dp))) {
                    Icon(Icons.Default.School, null, tint = Color(0xFF4D82F5), modifier = Modifier.align(Alignment.Center))
                }
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Ninguna") }, onClick = { onCarreraSelected(null); expanded = false })
            carreras.forEach { carrera ->
                DropdownMenuItem(
                    text = { Text(carrera.nombre ?: "") },
                    onClick = { onCarreraSelected(carrera); expanded = false }
                )
            }
        }
    }
}

@Composable
private fun ProfesorListItem(profesor: Profesor, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // TODO: Replace with real image
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.LightGray)) 
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(profesor.nombre, fontWeight = FontWeight.Bold)
                Text(profesor.correo, color = Color.Gray)
            }
            Button(onClick = {}, shape = RoundedCornerShape(8.dp)) {
                Text("Editar")
            }
        }
    }
}

@Composable
private fun EmptyStateProfesores() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(100.dp).clip(CircleShape).background(Color(0xFFF0F4FF)))
            Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFF4D82F5), modifier = Modifier.size(60.dp))
        }
        Spacer(Modifier.height(24.dp))
        Text("Comienza tu búsqueda", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Selecciona una carrera arriba para consultar los profesores disponibles.", color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
    }
}
