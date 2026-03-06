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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univapp.data.ActivityLog
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminActivityScreen(
    onBack: () -> Unit,
    vm: AdminActivityViewModel = viewModel()
) {
    val logs by vm.logs.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF5F6F8),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro de Actividad", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFF673AB7),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
            }
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(logs, key = { it.id }) { log ->
                    ActivityLogItem(log = log)
                }
                item {
                    Text("FIN DEL HISTORIAL", color = Color.Gray, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(vertical = 16.dp))
                }
            }
        }
    }
}

@Composable
private fun ActivityLogItem(log: ActivityLog) {
    val activityType = ActivityType.from(log.type)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(activityType.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(activityType.icon, contentDescription = null, tint = activityType.color)
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(activityType.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(log.timestamp, color = Color.Gray, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Text(log.description, fontSize = 14.sp)
            }
        }
    }
}
