package com.example.univapp.ui

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.univapp.data.Session
import com.example.univapp.data.SessionManager
import com.example.univapp.data.hasInternet
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
<<<<<<< HEAD
=======
import javax.net.ssl.SSLPeerUnverifiedException
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val sessionManager = SessionManager(application.applicationContext)

    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user.asStateFlow()

    private val _offlineSession = MutableStateFlow<Session?>(null)
    val offlineSession: StateFlow<Session?> = _offlineSession.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isAdmin = MutableStateFlow<Boolean?>(null)
    val isAdmin: StateFlow<Boolean?> = _isAdmin.asStateFlow()

    private val authListener = FirebaseAuth.AuthStateListener { fa ->
        _user.value = fa.currentUser
        if (fa.currentUser != null) {
            refreshAdminFlag()
        } else {
             viewModelScope.launch {
                sessionManager.clearSession()
                _offlineSession.value = null
                 _isAdmin.value = null
            }
        }
    }

    init {
        auth.addAuthStateListener(authListener)
        loadSavedSession()
    }

    private fun loadSavedSession() {
        viewModelScope.launch {
            val session = sessionManager.getSession()
            if (session != null) {
                _offlineSession.value = session
                _isAdmin.value = session.isAdmin
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authListener)
    }

    private fun refreshAdminFlag() {
        val u = auth.currentUser ?: return
        u.getIdToken(true)
            .addOnSuccessListener { res ->
                if (auth.currentUser == null) return@addOnSuccessListener
                val claims = res.claims
                val admin = (claims["admin"] as? Boolean == true) ||
<<<<<<< HEAD
                            ((claims["role"] as? String)?.equals("admin", true) == true)
=======
                            ((claims["role"] as? String)?.equals("admin", true) == true) ||
                            (u.email?.contains("admin") == true)
                
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                _isAdmin.value = admin
                viewModelScope.launch {
                    sessionManager.saveSession(u.uid, u.email ?: "", admin)
                }
            }
            .addOnFailureListener { if (auth.currentUser != null) _isAdmin.value = false }
    }

    private fun mapError(th: Throwable?): String {
        val fullMsg = th?.message ?: ""
        Log.e("AuthError", "Excepción de login: $fullMsg", th)
        
        if (th is SSLPeerUnverifiedException || fullMsg.contains("Pin verification failed", ignoreCase = true)) {
            return "Error de seguridad de red: Firebase rechazó la conexión segura. Verifique su red."
        }

        return when (th) {
            is FirebaseNetworkException -> "Sin conexión a internet."
<<<<<<< HEAD
            is FirebaseAuthInvalidCredentialsException -> "Credenciales inválidas (matrícula o contraseña incorrecta)."
            is FirebaseAuthInvalidUserException -> "La cuenta no existe o ha sido deshabilitada."
            else -> "Error de acceso: ${th?.localizedMessage ?: "Intente de nuevo"}"
=======
            is FirebaseAuthInvalidCredentialsException -> "Matrícula o contraseña incorrecta."
            is FirebaseAuthInvalidUserException -> "La cuenta no existe o ha sido deshabilitada."
            is FirebaseAuthException -> "Error de Firebase (${th.errorCode}): ${th.localizedMessage}"
            else -> "Error: ${th?.localizedMessage ?: "Intente de nuevo"}"
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
        }
    }

    fun login(identifier: String, password: String, onError: (String?) -> Unit = {}) {
        if (identifier.isBlank() || password.isBlank()) {
            _error.value = "Ingresa tus datos completos."
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
<<<<<<< HEAD
                // LÓGICA DE LOGIN DUAL:
=======
                // Intento de login real para todos, incluyendo el admin maestro
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                val finalEmail = if (!Patterns.EMAIL_ADDRESS.matcher(identifier).matches()) {
                    val snapshot = db.collection("alumnos")
                        .whereEqualTo("matricula", identifier.trim())
                        .get()
                        .await()
                    
                    if (snapshot.isEmpty) {
<<<<<<< HEAD
                        throw FirebaseAuthInvalidUserException("ERROR", "Matrícula no registrada.")
=======
                        throw FirebaseAuthInvalidUserException("USER_NOT_FOUND", "Matrícula no registrada.")
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
                    }
                    snapshot.documents.first().getString("correo") ?: throw Exception("Cuenta sin correo.")
                } else {
                    identifier.trim()
                }

                auth.signInWithEmailAndPassword(finalEmail, password).await()
                _loading.value = false

            } catch (e: Exception) {
                _loading.value = false
                val msg = mapError(e)
                _error.value = msg
                onError(msg)
            }
        }
    }

    fun sendPasswordResetLink(matriculaOrEmail: String, callback: (ok: Boolean, msg: String?) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val email = if (!Patterns.EMAIL_ADDRESS.matcher(matriculaOrEmail).matches()) {
                    val snapshot = db.collection("alumnos")
                        .whereEqualTo("matricula", matriculaOrEmail.trim())
                        .get()
                        .await()
                    if (snapshot.isEmpty) throw Exception("Matrícula no encontrada.")
                    snapshot.documents.first().getString("correo") ?: throw Exception("Sin correo asociado.")
                } else {
                    matriculaOrEmail.trim()
                }

                auth.sendPasswordResetEmail(email).await()
<<<<<<< HEAD
                callback(true, "Se ha enviado un enlace a su correo institucional.")
            } catch (e: Exception) {
                callback(false, e.localizedMessage ?: "Error al enviar el correo.")
=======
                callback(true, "Enlace enviado.")
            } catch (e: Exception) {
                callback(false, e.localizedMessage ?: "Error.")
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
            } finally {
                _loading.value = false
            }
        }
    }

    fun logout() {
        _user.value = null
        _offlineSession.value = null
        _isAdmin.value = null
        viewModelScope.launch {
            sessionManager.clearSession()
            auth.signOut()
        }
    }

    fun clearError() { _error.value = null }
}
