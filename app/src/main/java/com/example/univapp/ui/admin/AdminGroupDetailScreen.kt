
package com.example.univapp.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univapp.data.Alumno
import com.example.univapp.data.Carrera
import com.example.univapp.data.Grupo
import com.example.univapp.data.Profesor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGroupDetailScreen(
    groupId: String,
    onBack: () -> Unit,
    vm: AdminGroupDetailViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsState()

    // Carga los datos cuando la pantalla se muestra por primera vez.
    LaunchedEffect(key1 = groupId) {
        vm.load(groupId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Grupo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF7F7FB))
            )
        },
        containerColor = Color(0xFFF7F7FB)
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(uiState.error!!, color = Color.Gray, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                    Button(onClick = { vm.retry(groupId) }) { 
                        Text("Reintentar")
                    }
                }
            } else {
                GroupDetailContent(uiState)
            }
        }
    }
}

@Composable
private fun GroupDetailContent(uiState: GroupDetailUiState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp)
    ) {
        item {
            HeaderCard(group = uiState.group, carrera = uiState.carrera)
            Spacer(Modifier.height(24.dp))
        }

        item {
            SectionHeader("Información Académica")
            Spacer(Modifier.height(12.dp))
            InfoCards()
            Spacer(Modifier.height(24.dp))
        }

        item {
            SectionHeader("Tutor Asignado")
            Spacer(Modifier.height(12.dp))
            TutorCard(uiState.tutor)
            Spacer(Modifier.height(24.dp))
        }

        item {
            SectionHeader("Alumnos Asignados", onSeeAll = { /* TODO: Navegar a lista completa */ })
            Spacer(Modifier.height(12.dp))
        }

        if (uiState.alumnos.isEmpty()) {
            item {
                EmptyState(message = "No hay alumnos asignados a este grupo.")
            }
        } else {
            items(uiState.alumnos.take(4)) { student ->
                StudentItem(student)
            }
        }
    }
}

@Composable
private fun HeaderCard(group: Grupo?, carrera: Carrera?) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5).copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(vertical = 20.dp, horizontal = 24.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {
                AssistChip(
                    onClick = {},
                    label = { Text(group?.programType ?: "INGENIERÍA", fontWeight = FontWeight.Bold) },
                    colors = AssistChipDefaults.assistChipColors(labelColor = Color(0xFF673AB7)),
                    shape = RoundedCornerShape(8.dp)
                )
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.School, contentDescription = "Icono de grupo", tint = Color(0xFF673AB7))
                }
            }

            Spacer(Modifier.height(12.dp))
            Text(group?.nombre ?: "Grupo", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(carrera?.nombre ?: "Carrera no especificada", fontSize = 16.sp, color = Color.Gray)
            Spacer(Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text("Turno", fontSize = 13.sp, color = Color.Gray)
                    Text(group?.turno ?: "Matutino", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Column(Modifier.weight(1f)) {
                    Text("Total Alumnos", fontSize = 13.sp, color = Color.Gray)
                    Text((group?.numAlumnos ?: 0).toString(), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun InfoCards() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        InfoCard("Cuatrimestre", "Primer", Modifier.weight(1f)) // Placeholder
        InfoCard("Duración", "4 Meses", Modifier.weight(1f)) // Placeholder
    }
}

@Composable
private fun InfoCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = modifier) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun TutorCard(tutor: Profesor?) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        if (tutor == null) {
            Text("Tutor no asignado", modifier = Modifier.padding(16.dp), color = Color.Gray)
        } else {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Avatar(name = tutor.nombre)
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text(tutor.nombre, fontWeight = FontWeight.Bold)
                    Text("ID: ${tutor.numeroEmpleado ?: "No disponible"}", fontSize = 12.sp, color = Color.Gray)
                }
                Icon(Icons.Default.Email, contentDescription = "Contactar", tint = Color.Gray)
            }
        }
    }
}

@Composable
private fun StudentItem(student: Alumno) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(name = student.nombre)
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(student.nombre ?: "", fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("ID: ${student.matricula ?: ""}", fontSize = 12.sp, color = Color.Gray)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: (() -> Unit)? = null) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        if (onSeeAll != null) {
            TextButton(onClick = onSeeAll) {
                Text("Ver todos", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun Avatar(name: String?, modifier: Modifier = Modifier) {
    val initials = name?.split(" ")?.take(2)?.mapNotNull { it.firstOrNull()?.uppercase() }?.joinToString("") ?: "?"
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFFE0E0E0)),
        contentAlignment = Alignment.Center
    ) {
        Text(initials, fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}

@Composable
private fun EmptyState(message: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(Icons.Default.BrokenImage, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(60.dp))
        Text(message, color = Color.Gray, textAlign = TextAlign.Center)
    }
}
