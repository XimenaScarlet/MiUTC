package com.example.univapp.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univapp.data.Carrera
import com.example.univapp.data.Grupo
import com.example.univapp.data.Horario
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHorariosScreen(
    onBack: () -> Unit,
    onAddManually: () -> Unit,
    onImportExcel: () -> Unit,
    vm: AdminHorariosViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val isDetailView = uiState.selectedGrupo != null
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Horarios", fontWeight = FontWeight.Bold, color = if (isDetailView) Color.Black else Color.Black) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (uiState.selectedGrupo != null) {
                            vm.onBack()
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = if (isDetailView) Color.Black else Color.Black)
                    }
                },
                actions = {
                    if (isDetailView) {
                        IconButton(onClick = { /* TODO: Add action */ }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = "Calendario", tint = Color.Black)
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            if (isDetailView) {
                Box {
                    FloatingActionButton(
                        onClick = { showMenu = true },
                        containerColor = Color(0xFF673AB7),
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar Horario")
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
        Column(
            Modifier
                .padding(padding)
        ) {
            if (!isDetailView) {
                // --- Vista de Selección ---
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    CarreraSelector(
                        carreras = uiState.carreras,
                        selectedCarrera = uiState.selectedCarrera,
                        onCarreraSelected = { vm.onCarreraSelected(it) }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    if (uiState.isLoading) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                    } else if (uiState.selectedCarrera == null) {
                        EmptyState()
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            if (uiState.grupos.isEmpty()) {
                                item { EmptyState(message = "No hay grupos en esta carrera.") }
                            } else {
                                items(uiState.grupos, key = { it.id }) { grupo ->
                                    GrupoListItem(grupo = grupo, onClick = { vm.onGrupoSelected(grupo) })
                                }
                            }
                        }
                    }
                }
            } else {
                // --- Vista de Horario ---
                Column(Modifier.padding(horizontal = 16.dp)) {
                    GroupHeader(uiState.selectedGrupo, uiState.selectedCarrera)
                }
                WeeklyCalendarLikeCapture(horarios = uiState.horarios)
            }
        }
    }
}

@Composable
private fun GroupHeader(grupo: Grupo?, carrera: Carrera?) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Grupo: ${grupo?.nombre ?: ""}", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            AssistChip(
                onClick = { },
                label = { Text("Active", color = Color(0xFF4CAF50)) },
                colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)),
                border = null
            )
        }
        Text("${carrera?.nombre ?: ""} • Jan - Jun 2024", color = Color.Gray)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CarreraSelector(
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
                    text = { Text(carrera.nombre ?: "Carrera sin nombre") },
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
private fun GrupoListItem(grupo: Grupo, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFE8F0FE), CircleShape)
                , contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Groups, contentDescription = null, tint = Color(0xFF007BFF))
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(grupo.nombre ?: "", fontWeight = FontWeight.Bold)
                Text("Grupo Activo", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun EmptyState(message: String? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Default.School, contentDescription = null, tint = Color.LightGray.copy(alpha = 0.5f), modifier = Modifier.size(100.dp))
            Text(message ?: "", fontSize = 18.sp, color = Color.Gray)
        }
    }
}

@Composable
fun WeeklyCalendarLikeCapture(
    horarios: List<Horario>,
    modifier: Modifier = Modifier
) {
    val cardBg = Color.White
    val border = Color(0xFFE0E0E0)
    val softText = Color(0xFF6B7280)
    val primary = Color(0xFF6C5DD3)

    val locale = Locale("es", "MX")
    val monday = LocalDate.now().with(DayOfWeek.MONDAY)
    val days = (0..4).map { monday.plusDays(it.toLong()) }

    val timeSlots = generateHalfHourSlots(startHour = 5, startMinute = 0, endHour = 9, endMinute = 30)

    val timeColWidth = 60.dp
    val slotHeight = 44.dp
    val headerHeight = 56.dp

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val dayColWidth = (maxWidth - timeColWidth) / 5f

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp)
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBg),
            border = BorderStroke(1.dp, border),
            shape = RoundedCornerShape(24.dp)
        ) {
            // --- Header (GMT-6 + días) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F6F8))
                    .border(1.dp, border)
            ) {
                Box(
                    modifier = Modifier
                        .width(timeColWidth)
                        .height(headerHeight)
                        .border(1.dp, border),
                    contentAlignment = Alignment.Center
                ) {
                    Text("GMT-6", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = softText)
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    days.forEachIndexed { idx, date ->
                        val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                            .replace(".", "")
                            .replaceFirstChar { it.uppercase(locale) }

                        val isSelected = idx == 0
                        Box(
                            modifier = Modifier
                                .width(dayColWidth)
                                .height(headerHeight)
                                .border(1.dp, border),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(dayName, fontSize = 12.sp, color = softText, fontWeight = FontWeight.Medium)
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(primary, RoundedCornerShape(999.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = date.dayOfMonth.toString(),
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                } else {
                                    Text(
                                        text = date.dayOfMonth.toString(),
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // --- Body (horas + grid) ---
            val vScroll = rememberScrollState()

            Row(Modifier.fillMaxSize()) {

                // Columna de tiempos
                Column(
                    modifier = Modifier
                        .width(timeColWidth)
                        .fillMaxHeight()
                        .background(Color(0xFFF9FAFB))
                        .verticalScroll(vScroll)
                        .border(1.dp, border),
                    horizontalAlignment = Alignment.End
                ) {
                    Spacer(Modifier.height(8.dp))
                    timeSlots.forEach { t ->
                        Box(
                            modifier = Modifier
                                .height(slotHeight)
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Text(t, color = softText, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                // Grid
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(vScroll)
                        .background(cardBg)
                ) {
                    GridLines(
                        rowsCount = timeSlots.size,
                        colsCount = days.size,
                        rowHeight = slotHeight,
                        colWidth = dayColWidth,
                        lineColor = border.copy(alpha = 0.7f)
                    )

                    if (horarios.isEmpty()) {
                        Box(
                            modifier = Modifier.matchParentSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("📅", fontSize = 34.sp)
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    "No hay clases programadas",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Text(
                                    "¡Agrega un horario para comenzar!",
                                    color = softText,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    } else {
                        // luego aquí pintamos los bloques de tus horarios encima del grid
                    }
                }
            }
        }
    }
}

private fun generateHalfHourSlots(
    startHour: Int,
    startMinute: Int,
    endHour: Int,
    endMinute: Int
): List<String> {
    val result = mutableListOf<String>()
    var h = startHour
    var m = startMinute

    fun fmt(hh: Int, mm: Int) = String.format("%02d:%02d", hh, mm)

    while (h < endHour || (h == endHour && m <= endMinute)) {
        result.add(fmt(h, m))
        m += 30
        if (m >= 60) {
            m -= 60
            h += 1
        }
    }
    return result
}

@Composable
private fun GridLines(
    rowsCount: Int,
    colsCount: Int,
    rowHeight: Dp,
    colWidth: Dp,
    lineColor: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight * rowsCount)
    ) {
        val w = size.width
        val h = size.height

        val colPx = colWidth.toPx()
        for (i in 1 until colsCount) {
            val x = colPx * i
            drawLine(lineColor, Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
        }

        val rowPx = rowHeight.toPx()
        for (i in 1..rowsCount) {
            val y = rowPx * i
            drawLine(lineColor, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
        }
    }
}
