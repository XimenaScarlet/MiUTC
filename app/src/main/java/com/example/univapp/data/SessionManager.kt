package com.example.univapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

data class Session(
    val userId: String,
    val email: String,
    val isAdmin: Boolean
)

class SessionManager(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val IS_ADMIN_KEY = booleanPreferencesKey("is_admin")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveSession(userId: String, email: String, isAdmin: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[EMAIL_KEY] = email
            preferences[IS_ADMIN_KEY] = isAdmin
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    suspend fun getSession(): Session? {
        val preferences = context.dataStore.data.first()
        val isLoggedIn = preferences[IS_LOGGED_IN_KEY] ?: false
        if (!isLoggedIn) return null

        val userId = preferences[USER_ID_KEY]
        val email = preferences[EMAIL_KEY]
        val isAdmin = preferences[IS_ADMIN_KEY]

        return if (userId != null && email != null && isAdmin != null) {
            Session(userId, email, isAdmin)
        } else null
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}

fun hasInternet(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val capabilities = cm.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
