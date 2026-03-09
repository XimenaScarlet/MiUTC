package com.example.univapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univapp.data.Session
import com.example.univapp.location.LocationHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SOSViewModel @Inject constructor(
    private val locationHelper: LocationHelper,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val TAG = "SOS_DEBUG"
    private var trackingJob: Job? = null

    private val _isTracking = MutableStateFlow(false)
    val isTracking = _isTracking.asStateFlow()

    private var offlineSession: Session? = null
    fun setOfflineSession(session: Session?) { this.offlineSession = session }

    fun startTracking() {
        if (trackingJob != null) return
        
        val uid = auth.currentUser?.uid ?: offlineSession?.userId ?: return
        val email = auth.currentUser?.email ?: offlineSession?.email ?: "Usuario"
        
        Log.d(TAG, "Iniciando proceso SOS. UID: $uid, Email: $email")
        _isTracking.value = true

        trackingJob = viewModelScope.launch {
            // 1. Obtener nombre del alumno
            var nombreAlumno = "Alumno"
            try {
                val doc = db.collection("alumnos").document(uid).get().await()
                nombreAlumno = doc.getString("nombre") ?: email.substringBefore("@")
                Log.d(TAG, "Nombre identificado: $nombreAlumno")
            } catch (e: Exception) {
                Log.e(TAG, "Error obteniendo nombre de alumno: ${e.message}")
            }

            // 2. Crear documento inicial SOS (AWAIT)
            val initialData = hashMapOf(
                "alumnoId" to uid,
                "alumnoNombre" to nombreAlumno,
                "email" to email,
                "active" to true,
                "status" to "active",
                "timestamp" to FieldValue.serverTimestamp()
            )

            try {
                db.collection("sos_alerts").document(uid)
                    .set(initialData, SetOptions.merge())
                    .await()
                Log.d(TAG, "Documento SOS inicial creado/actualizado exitosamente.")
            } catch (e: Exception) {
                Log.e(TAG, "ERROR CRÍTICO: No se pudo crear el documento SOS: ${e.message}")
                _isTracking.value = false
                return@launch
            }

            // 3. Loop de actualización de ubicación
            while (_isTracking.value) {
                val latLng = locationHelper.getCurrentLocation()
                if (latLng != null) {
                    val updateData = hashMapOf(
                        "location" to GeoPoint(latLng.latitude, latLng.longitude),
                        "timestamp" to FieldValue.serverTimestamp()
                    )
                    try {
                        db.collection("sos_alerts").document(uid)
                            .set(updateData, SetOptions.merge())
                            .await()
                        Log.d(TAG, "Ubicación actualizada: ${latLng.latitude}, ${latLng.longitude}")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error actualizando ubicación en Firestore: ${e.message}")
                    }
                } else {
                    Log.w(TAG, "LocationHelper devolvió NULL. No se pudo obtener la ubicación en este ciclo.")
                }
                delay(5000)
            }
        }
    }

    fun stopTracking() {
        val uid = auth.currentUser?.uid ?: offlineSession?.userId ?: return
        Log.d(TAG, "Deteniendo seguimiento SOS para UID: $uid")
        
        _isTracking.value = false
        trackingJob?.cancel()
        trackingJob = null
        
        viewModelScope.launch {
            try {
                db.collection("sos_alerts").document(uid).update(
                    mapOf(
                        "active" to false,
                        "status" to "ended",
                        "timestamp" to FieldValue.serverTimestamp()
                    )
                ).await()
                Log.d(TAG, "SOS marcado como inactivo correctamente.")
            } catch (e: Exception) {
                Log.e(TAG, "Error al cerrar el SOS en Firestore: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        trackingJob?.cancel()
    }
}
