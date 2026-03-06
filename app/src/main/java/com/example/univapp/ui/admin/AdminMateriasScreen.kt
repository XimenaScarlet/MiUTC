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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import com.example.univapp.data.Grupo
import com.example.univapp.data.Materia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMateriasScreen(
    onBack: () -> Unit,
    onOpenMateria: (String) -> Unit,
    onAddManually: () -> Unit,
    onImportExcel: () -> Unit,
    vm: AdminMateriasViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Materias", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (uiState.selectedGrupo != null) {
                            vm.onGrupoSelected(null)
                        } else if (uiState.selectedCarrera != null) {
                            vm.onCarreraSelected(null)
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Navigate to profile */ }) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        ) {
                            Icon(Icons.Default.Person, null, tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            if (uiState.selectedGrupo != null) {
                Box {
                    FloatingActionButton(
                        onClick = { showMenu = true },
                        containerColor = Color(0xFF6200EE),
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, "Añadir Materia")
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
        Column(Modifier.padding(padding).padding(16.dp)) {
            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            } else {
                CarreraSelectorMaterias(uiState.carreras, uiState.selectedCarrera, { vm.onCarreraSelected(it) })
                Spacer(Modifier.height(24.dp))

                if (uiState.selectedCarrera == null) {
                    EmptyStateMaterias()
                } else if (uiState.selectedGrupo == null) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.grupos, key = { it.id }) { grupo ->
                            GrupoListItem(grupo = grupo, onClick = { vm.onGrupoSelected(grupo) })
                        }
                    }
                } else {
                    if (uiState.materias.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No hay materias en este grupo.") }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(uiState.materias, key = { it.id }) { materia ->
                                MateriaListItem(materia = materia, onClick = { onOpenMateria(materia.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CarreraSelectorMaterias(
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
                Box(modifier = Modifier.size(40.dp).background(Color(0xFFEDE7F6), RoundedCornerShape(8.dp))) {
                    Icon(Icons.Default.School, null, tint = Color(0xFF6200EE), modifier = Modifier.align(Alignment.Center))
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
private fun GrupoListItem(grupo: Grupo, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFE8F0FE)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Groups, contentDescription = null, tint = Color(0xFF007BFF))
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(grupo.nombre ?: "", fontWeight = FontWeight.Bold)
                Text("Ver materias", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
private fun MateriaListItem(materia: Materia, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(materia.nombre, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    val hasProfesor = !materia.profesorId.isNullOrBlank()
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(if (hasProfesor) Color.Green else Color.Red)) { }
                    Text(if(hasProfesor) "Profesor Asignado" else "Sin Profesor", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
private fun EmptyStateMaterias() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(24.dp)).background(Color.White))
            Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFF6200EE), modifier = Modifier.size(60.dp))
            Box(modifier = Modifier.align(Alignment.TopEnd).offset(x = 8.dp, y = (-8).dp)) {
                Icon(Icons.Default.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(24.dp).clip(CircleShape).background(Color.White))
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Selecciona una carrera", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Selecciona una carrera arriba para ver las materias gestionadas.", color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
        Spacer(Modifier.height(24.dp))
        Text("COMENZAR", color = Color(0xFF6200EE).copy(alpha = 0.5f), fontWeight = FontWeight.Bold)
        Icon(Icons.Default.KeyboardArrowUp, null, tint = Color(0xFF6200EE).copy(alpha = 0.5f))
    }
}
