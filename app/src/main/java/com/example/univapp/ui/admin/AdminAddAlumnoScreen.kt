package com.example.univapp.ui.admin

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
<<<<<<< HEAD
import androidx.lifecycle.viewmodel.compose.viewModel
=======
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.univapp.ui.util.AppScaffold
import com.example.univapp.ui.util.ValidatedTextField
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddAlumnoScreen(
<<<<<<< HEAD
    carreraId: String, // ID real recibido de la navegación
    groupId: String,   // ID real recibido de la navegación
=======
    carreraId: String,
    groupId: String,
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
    onBack: () -> Unit,
    vm: AdminAddAlumnoViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf<String?>(null) }
    var fechaNacimiento by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    var isGeneroExpanded by remember { mutableStateOf(false) }
    var isContactVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val teal = Color(0xFF0F6C6D)
<<<<<<< HEAD
    val darkText = Color(0xFF1D1B20)
=======
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
    val localeSpanish = Locale("es", "ES")

    val isOldEnough = remember(fechaNacimiento) {
        if (fechaNacimiento.isBlank()) false
        else {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy", localeSpanish)
                val birthDate = sdf.parse(fechaNacimiento) ?: return@remember false
                val today = Calendar.getInstance()
                val birth = Calendar.getInstance().apply { time = birthDate }
                var age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
                if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) age--
                age >= 15
            } catch (e: Exception) { false }
        }
    }

    LaunchedEffect(uiState.onSaveSuccess) {
        if (uiState.onSaveSuccess) onBack()
<<<<<<< HEAD
    }

    LaunchedEffect(matricula) {
        email = if (matricula.isNotBlank()) "$matricula@alumno.utc.edu.mx" else ""
    }

=======
    }

    LaunchedEffect(matricula) {
        email = if (matricula.isNotBlank()) "$matricula@alumno.utc.edu.mx" else ""
    }

>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
<<<<<<< HEAD
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val sdf = SimpleDateFormat("dd/MM/yyyy", localeSpanish)
                            fechaNacimiento = sdf.format(Date(it))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("ACEPTAR", color = teal, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("CANCELAR", color = Color.Gray)
                }
            },
            shape = RoundedCornerShape(32.dp),
            colors = DatePickerDefaults.colors(containerColor = Color.White)
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = "SELECCIONAR FECHA",
                        modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = teal,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                },
                headline = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 16.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val dateText = datePickerState.selectedDateMillis?.let {
                            SimpleDateFormat("d 'de' MMMM, yyyy", localeSpanish).format(Date(it))
                        } ?: "Selecciona"
                        Text(
                            text = dateText.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = darkText
                        )
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(teal.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.EditCalendar, contentDescription = null, tint = teal, modifier = Modifier.size(20.dp))
                        }
                    }
                },
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White,
                    titleContentColor = teal,
                    headlineContentColor = darkText,
                    todayContentColor = teal,
                    selectedDayContainerColor = teal,
                    selectedDayContentColor = Color.White,
                    weekdayContentColor = Color.Gray,
                    dayContentColor = darkText,
                    navigationContentColor = Color.Gray
=======
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val sdf = SimpleDateFormat("dd/MM/yyyy", localeSpanish)
                        fechaNacimiento = sdf.format(Date(it))
                    }
                    showDatePicker = false
                }) { Text("ACEPTAR", color = teal, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("CANCELAR", color = Color.Gray) }
            },
            shape = RoundedCornerShape(32.dp)
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = teal,
                    todayContentColor = teal,
                    headlineContentColor = teal
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                )
            )
        }
    }

    AppScaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nuevo Estudiante", fontWeight = FontWeight.ExtraBold, color = teal) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = teal) } },
<<<<<<< HEAD
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF7F7FB))
=======
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
            )
        },
        bottomBar = {
            val canSave = matricula.isNotBlank() && nombre.isNotBlank() && genero != null && isOldEnough
            Surface(shadowElevation = 8.dp) {
<<<<<<< HEAD
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (fechaNacimiento.isNotBlank() && !isOldEnough) {
                        Text(
                            "El alumno debe ser mayor de 15 años.",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Button(
                        onClick = {
                            // CORRECCIÓN: Ahora pasamos carreraId y groupId reales al ViewModel
                            vm.saveAlumno(
                                matricula = matricula,
                                nombre = nombre,
                                carreraId = carreraId,
                                grupoId = groupId,
                                genero = genero!!,
                                fechaNacimiento = fechaNacimiento,
                                email = email,
                                telefono = telefono,
                                direccion = direccion
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
                        enabled = canSave,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = teal)
                    ) {
                        Icon(Icons.Default.Save, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Guardar Registro", fontSize = 16.sp)
=======
                Column(modifier = Modifier.navigationBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (fechaNacimiento.isNotBlank() && !isOldEnough) {
                        Text("El alumno debe ser mayor de 15 años.", color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                    Button(
                        onClick = {
                            vm.saveAlumno(matricula, nombre, carreraId, groupId, genero!!, fechaNacimiento, email, telefono, direccion)
                        },
                        modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
                        enabled = canSave && !uiState.isLoading,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = teal)
                    ) {
                        if (uiState.isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        else {
                            Icon(Icons.Default.Save, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Guardar Registro", fontSize = 16.sp)
                        }
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { ProfilePicture() }

<<<<<<< HEAD
            // --- INFO PRINCIPAL ---
            item {
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(
                        Modifier.padding(16.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = matricula,
                            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 15) matricula = it },
                            label = { Text("Matrícula", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            textStyle = TextStyle(textAlign = TextAlign.Center)
                        )
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { if (it.length <= 70) nombre = it },
                            label = { Text("Nombre Completo", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            textStyle = TextStyle(textAlign = TextAlign.Center)
=======
            item {
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        ValidatedTextField(
                            value = matricula,
                            onValueChange = { matricula = it },
                            label = "Matrícula",
                            maxLength = 15,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )
                        ValidatedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = "Nombre Completo",
                            maxLength = 70
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Correo Institucional", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            leadingIcon = { Icon(Icons.Default.Email, null, tint = teal) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(textAlign = TextAlign.Center),
<<<<<<< HEAD
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color.Black,
                                disabledBorderColor = Color.LightGray
                            )
=======
                            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black)
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                        )
                    }
                }
            }

<<<<<<< HEAD
            // --- DATOS PERSONALES ---
            item {
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(
                        Modifier.padding(16.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            DropdownSelector(
                                label = "Género",
                                options = listOf("Masculino", "Femenino", "Otro"),
                                selectedOption = genero,
                                onOptionSelected = { genero = it },
                                modifier = Modifier.weight(1f),
                                expanded = isGeneroExpanded,
                                onExpandedChange = { isGeneroExpanded = it }
                            )
                            
                            Box(modifier = Modifier.weight(1.2f).clickable { showDatePicker = true }) {
                                OutlinedTextField(
                                    value = if (fechaNacimiento.isEmpty()) "Seleccionar" else fechaNacimiento,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("F. Nacimiento", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = false,
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = if (fechaNacimiento.isEmpty()) 12.sp else 16.sp
                                    ),
                                    trailingIcon = { Icon(Icons.Default.CalendarToday, null, tint = teal) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        disabledTextColor = Color.Black,
                                        disabledBorderColor = Color.Gray,
                                        disabledLabelColor = Color.Gray
                                    )
=======
            item {
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                    Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        DropdownSelector(
                            label = "Género",
                            options = listOf("Masculino", "Femenino", "Otro"),
                            selectedOption = genero,
                            onOptionSelected = { genero = it },
                            modifier = Modifier.weight(1f),
                            expanded = isGeneroExpanded,
                            onExpandedChange = { isGeneroExpanded = it }
                        )
                        Box(modifier = Modifier.weight(1.2f).clickable { showDatePicker = true }) {
                            OutlinedTextField(
                                value = if (fechaNacimiento.isEmpty()) "Seleccionar" else fechaNacimiento,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("F. Nacimiento", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = false,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = if (fechaNacimiento.isEmpty()) 12.sp else 16.sp),
                                trailingIcon = { Icon(Icons.Default.CalendarToday, null, tint = teal) },
                                colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black)
                            )
                        }
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(modifier = Modifier.fillMaxWidth().clickable { isContactVisible = !isContactVisible }.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Icon(Icons.Default.ContactPhone, null, tint = teal)
                            Spacer(Modifier.width(12.dp))
                            Text("Información de Contacto", fontWeight = FontWeight.Bold)
                            Icon(if (isContactVisible) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null)
                        }
                        AnimatedVisibility(visible = isContactVisible) {
                            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                ValidatedTextField(
                                    value = telefono,
                                    onValueChange = { telefono = it },
                                    label = "Teléfono",
                                    maxLength = 10,
                                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone)
                                )
                                ValidatedTextField(
                                    value = direccion,
                                    onValueChange = { direccion = it },
                                    label = "Dirección",
                                    maxLength = 100
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                                )
                            }
                        }
                    }
                }
            }
<<<<<<< HEAD

            // --- CONTACTO ---
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable { isContactVisible = !isContactVisible }.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.ContactPhone, null, tint = teal)
                            Spacer(Modifier.width(12.dp))
                            Text("Información de Contacto", fontWeight = FontWeight.Bold)
                            Spacer(Modifier.width(8.dp))
                            Icon(if (isContactVisible) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null)
                        }
                        
                        AnimatedVisibility(visible = isContactVisible) {
                            Column(
                                Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                OutlinedTextField(
                                    value = telefono,
                                    onValueChange = { if(it.all { c -> c.isDigit() } && it.length <= 10) telefono = it },
                                    label = { Text("Teléfono", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = TextStyle(textAlign = TextAlign.Center)
                                )
                                OutlinedTextField(
                                    value = direccion,
                                    onValueChange = { direccion = it },
                                    label = { Text("Dirección", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = TextStyle(textAlign = TextAlign.Center)
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }

            if (uiState.isLoading) {
                item { Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = teal) } }
            }
=======
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
            uiState.error?.let { item { Text(it, color = Color.Red, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) } }
        }
    }
}

@Composable
private fun ProfilePicture() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
        Box(contentAlignment = Alignment.Center) {
<<<<<<< HEAD
            Box(
                modifier = Modifier.size(110.dp).clip(CircleShape).background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = Color.Gray, modifier = Modifier.size(60.dp))
            }
            IconButton(
                onClick = { },
                modifier = Modifier.size(36.dp).align(Alignment.BottomEnd).clip(CircleShape).background(Color(0xFF0F6C6D))
            ) {
=======
            Box(modifier = Modifier.size(110.dp).clip(CircleShape).background(Color(0xFFE0E0E0)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, null, tint = Color.Gray, modifier = Modifier.size(60.dp))
            }
            IconButton(onClick = { }, modifier = Modifier.size(36.dp).align(Alignment.BottomEnd).clip(CircleShape).background(Color(0xFF0F6C6D))) {
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                Icon(Icons.Default.CameraAlt, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("Foto de Perfil", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
<<<<<<< HEAD
private fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange(it) }, modifier = modifier) {
        OutlinedTextField(
            value = selectedOption ?: "Seleccionar",
            onValueChange = {},
            label = { Text(label, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = if (selectedOption == null) 12.sp else 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black)
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                    onClick = { onOptionSelected(option); onExpandedChange(false) }
                )
=======
private fun DropdownSelector(label: String, options: List<String>, selectedOption: String?, onOptionSelected: (String) -> Unit, modifier: Modifier = Modifier, expanded: Boolean, onExpandedChange: (Boolean) -> Unit) {
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange(it) }, modifier = modifier) {
        OutlinedTextField(value = selectedOption ?: "Seleccionar", onValueChange = {}, label = { Text(label, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) }, readOnly = true, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }, modifier = Modifier.fillMaxWidth().menuAnchor(), shape = RoundedCornerShape(12.dp), textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = if (selectedOption == null) 12.sp else 16.sp), colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black))
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) }, onClick = { onOptionSelected(option); onExpandedChange(false) })
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
            }
        }
    }
}
