package com.example.univapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@IgnoreExtraProperties
data class SosAlert(
    val alumnoId: String = "",
    val alumnoNombre: String = "",
    val email: String = "",
    val location: GeoPoint? = null,
    val active: Boolean = false,
    val status: String = "",
    @ServerTimestamp val timestamp: Date? = null
)

@HiltViewModel
class AdminSosViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
    private val TAG = "SOS_ADMIN_DEBUG"
    private val _alerts = MutableStateFlow<List<SosAlert>>(emptyList())
    val alerts = _alerts.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private var listenerRegistration: ListenerRegistration? = null

    fun startListening() {
        if (listenerRegistration != null) return
        
        Log.d(TAG, "Iniciando escucha de alertas SOS activas...")
        
        listenerRegistration = db.collection("sos_alerts")
            .whereEqualTo("active", true)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error en SnapshotListener: ${error.message}", error)
                    _error.value = "Error de conexión con el servidor SOS: ${error.localizedMessage}"
                    return@addSnapshotListener
                }
                
                val alertList = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        doc.toObject(SosAlert::class.java)?.copy(alumnoId = doc.id)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parseando documento ${doc.id}: ${e.message}")
                        null
                    }
                } ?: emptyList()
                
                Log.d(TAG, "Snapshot recibido. Alertas activas encontradas: ${alertList.size}")
                _alerts.value = alertList
                _error.value = null
            }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
        Log.d(TAG, "Listener SOS detenido")
    }
}
