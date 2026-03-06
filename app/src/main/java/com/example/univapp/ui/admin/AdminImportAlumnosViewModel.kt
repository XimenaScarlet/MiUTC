package com.example.univapp.ui.admin

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univapp.data.Alumno
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.poiji.bind.Poiji
import com.poiji.exception.PoijiExcelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.InputStream

class AdminImportAlumnosViewModel : ViewModel() {

    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val importState: StateFlow<ImportState> = _importState

    fun importAlumnosFromUri(uri: Uri, inputStream: InputStream?) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                val alumnos = inputStream?.use { stream ->
                    Poiji.fromExcel(stream, PoijiExcelType.XLSX, Alumno::class.java)
                }
                if (alumnos.isNullOrEmpty()) {
                    _importState.value = ImportState.Error("El archivo está vacío o no tiene el formato correcto.")
                    return@launch
                }

                val db = Firebase.firestore
                val batch = db.batch()

                alumnos.forEach { alumno ->
                    val docRef = db.collection("alumnos").document(alumno.id)
                    batch.set(docRef, alumno)
                }

                batch.commit().await()
                _importState.value = ImportState.Success(alumnos.size)
            } catch (e: Exception) {
                _importState.value = ImportState.Error(e.message ?: "Ocurrió un error al importar los alumnos.")
            }
        }
    }

    fun resetState() {
        _importState.value = ImportState.Idle
    }
}

sealed class ImportState {
    object Idle : ImportState()
    object Loading : ImportState()
    data class Success(val count: Int) : ImportState()
    data class Error(val message: String) : ImportState()
}
