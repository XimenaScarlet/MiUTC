package com.example.univapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.univapp.data.Alumno
import com.example.univapp.data.AlumnosRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlumnosVM(
    private val repo: AlumnosRepo = AlumnosRepo()
) : ViewModel() {

    val alumnos: StateFlow<List<Alumno>> =
        repo.stream().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregar(nombre: String, matricula: String, correo: String) {
        viewModelScope.launch {
            repo.add(
                Alumno(
                    nombre = nombre,
                    matricula = matricula,
                    correo = correo
                )
            )
        }
    }

    fun borrar(id: String) = viewModelScope.launch { repo.delete(id) }
}
