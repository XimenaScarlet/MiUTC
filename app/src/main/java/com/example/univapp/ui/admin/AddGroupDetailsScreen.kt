package com.example.univapp.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univapp.data.Profesor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupDetailsScreen(
    onBack: () -> Unit,
    onNext: (String, String, String) -> Unit,
    vm: AdminGruposViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsState()
    var groupName by remember { mutableStateOf("") }
    var programType by remember { mutableStateOf("Ingeniería") }
    var expanded by remember { mutableStateOf(false) }
    var selectedTutor by remember { mutableStateOf<Profesor?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Grupo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Información Académica", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Complete los detalles a continuación para registrar un nuevo grupo en el sistema universitario.", fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("Nombre del Grupo") },
                    placeholder = { Text("Ej: 1°A") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Tipo de Programa", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                SegmentedButtonRow(programType = programType, onProgramTypeChange = { programType = it })


                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    OutlinedTextField(
                        value = selectedTutor?.nombre ?: "Seleccionar Tutor",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tutor") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        uiState.profesores.forEach { tutor ->
                            DropdownMenuItem(
                                text = { Text(tutor.nombre ?: "") },
                                onClick = {
                                    selectedTutor = tutor
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { onNext(groupName, programType, selectedTutor!!.id) },
                    enabled = groupName.isNotBlank() && selectedTutor != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Siguiente")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun SegmentedButtonRow(programType: String, onProgramTypeChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .background(Color.LightGray)
    ) {
        val engineeringColors = if (programType == "Ingeniería") {
            ButtonDefaults.buttonColors(containerColor = Color.White)
        } else {
            ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Gray)
        }
        val tsuColors = if (programType == "TSU") {
            ButtonDefaults.buttonColors(containerColor = Color.White)
        } else {
            ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Gray)
        }

        Button(
            onClick = { onProgramTypeChange("Ingeniería") },
            colors = engineeringColors,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text("Ingeniería")
        }
        Button(
            onClick = { onProgramTypeChange("TSU") },
            colors = tsuColors,
             shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text("TSU")
        }
    }
}
