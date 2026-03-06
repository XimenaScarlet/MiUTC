package com.example.univapp.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Alumno(
    @get:Exclude var id: String = "",
    var matricula: String? = null,
    var nombre: String? = null,
    var correo: String? = null,
    var carreraId: String? = null,
    var grupoId: String? = null,
    var estatusAcademico: String = "Activo",
    var genero: String = "M",
    var edad: Int? = 0,
    var activo: Boolean? = true,
    var telefono: String? = null,
    var fechaNacimiento: String? = null,
    var fechaIngreso: String? = null,
    var nombreContacto: String? = null,
    var telefonoEmergencia: String? = null,
    var direccion: String? = null,
    @ServerTimestamp val createdAt: Date? = null
)
