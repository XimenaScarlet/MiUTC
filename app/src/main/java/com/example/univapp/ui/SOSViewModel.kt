package com.example.univapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univapp.data.Session
import com.example.univapp.location.LocationHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
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
    private var trackingJob: Job? = null

    private val _isTracking = MutableStateFlow(false)
    val isTracking = _isTracking.asStateFlow()

    private var offlineSession: Session? = null
    fun setOfflineSession(session: Session?) { this.offlineSession = session }

    fun startTracking() {
        val uid = auth.currentUser?.uid ?: offlineSession?.userId ?: return
        val email = auth.currentUser?.email ?: offlineSession?.email ?: "Usuario"

        if (trackingJob != null) return
        _isTracking.value = true

        viewModelScope.launch {
            var nombreAlumno = "Alumno"
            try {
                val docById = db.collection("alumnos").document(uid).get().await()
                if (docById.exists()) {
                    nombreAlumno = docById.getString("nombre") ?: "Alumno"
                } else {
                    val query = db.collection("alumnos").whereEqualTo("correo", email).get().await()
                    if (!query.isEmpty) {
                        nombreAlumno = query.documents[0].getString("nombre") ?: "Alumno"
                    }
                }
            } catch (e: Exception) {
                nombreAlumno = email.substringBefore("@")
            }

            val initialData = hashMapOf(
                "alumnoId" to uid,
                "alumnoNombre" to nombreAlumno,
                "email" to email,
                "active" to true,
                "status" to "active",
                "timestamp" to FieldValue.serverTimestamp()
            )
            db.collection("sos_alerts").document(uid).set(initialData)
        }

        trackingJob = viewModelScope.launch {
            while (_isTracking.value) {
                val latLng = locationHelper.getCurrentLocation()
                if (latLng != null) {
                    val updateData = mapOf(
                        "location" to GeoPoint(latLng.latitude, latLng.longitude),
                        "timestamp" to FieldValue.serverTimestamp()
                    )
                    db.collection("sos_alerts").document(uid).update(updateData)
                }
                delay(5000)
            }
        }
    }

    fun stopTracking() {
        val uid = auth.currentUser?.uid ?: offlineSession?.userId ?: return
        _isTracking.value = false
        trackingJob?.cancel()
        trackingJob = null
        
        db.collection("sos_alerts").document(uid).update(
            "active", false,
            "status", "ended",
            "timestamp", FieldValue.serverTimestamp()
        )
    }

    override fun onCleared() {
        super.onCleared()
        trackingJob?.cancel()
    }
}
