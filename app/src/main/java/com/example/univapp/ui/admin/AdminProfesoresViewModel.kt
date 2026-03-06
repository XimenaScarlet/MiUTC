package com.example.univapp.ui.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univapp.data.Carrera
import com.example.univapp.data.Profesor
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AdminProfesoresUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val carreras: List<Carrera> = emptyList(),
    val profesores: List<Profesor> = emptyList(),
    val selectedCarrera: Carrera? = null
)

class AdminProfesoresViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _uiState = MutableStateFlow(AdminProfesoresUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCarreras()
    }

    private fun loadCarreras() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val snapshot = db.collection("carreras").get().await()
                val carreras = snapshot.documents.mapNotNull { doc ->
                    try {
                        doc.toObject(Carrera::class.java)?.apply { id = doc.id }
                    } catch (e: Exception) {
                        Log.e("AdminProfesoresVM", "Error deserializing carrera: ${doc.id}", e)
                        null
                    }
                }
                _uiState.update { it.copy(carreras = carreras, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onCarreraSelected(carrera: Carrera?) {
        _uiState.update { it.copy(selectedCarrera = carrera, profesores = emptyList()) }
        if (carrera == null) return

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val snapshot = db.collection("profesores").whereEqualTo("carreraId", carrera.id).get().await()
                val profesores = snapshot.documents.mapNotNull { doc ->
                    try {
                        doc.toObject(Profesor::class.java)?.apply { id = doc.id }
                    } catch (e: Exception) {
                        Log.e("AdminProfesoresVM", "Error deserializing profesor: ${doc.id}", e)
                        null
                    }
                }
                _uiState.update { it.copy(profesores = profesores, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
