package com.example.univapp.ui.maps

import com.example.univapp.data.network.MapsApiService
import com.example.univapp.di.NetworkModule
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

data class EtaResult(
    val durationSeconds: Long,
    val distanceMeters: Long
)

/**
 * ETA con OSRM pública migrado a Retrofit para mayor seguridad.
 */
@Singleton
class EtaFetcher @Inject constructor(
    private val apiService: MapsApiService
) {
    suspend fun fetchEtaOsrm(origin: LatLng, dest: LatLng): EtaResult? =
        com.example.univapp.ui.maps.fetchEtaOsrm(origin, dest)
}

/**
 * Función global para compatibilidad con el código existente.
 * Usa el puente estático de NetworkModule para obtener el servicio de Retrofit.
 */
suspend fun fetchEtaOsrm(origin: LatLng, dest: LatLng): EtaResult? =
    withContext(Dispatchers.IO) {
        val coords = "${origin.longitude},${origin.latitude};${dest.longitude},${dest.latitude}"

        runCatching {
<<<<<<< HEAD
            val response = NetworkModule.mapsApiService.getOsrmRoute(url)
=======
            val response = NetworkModule.mapsApiService.getOsrmRoute(coords = coords)
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
            if (response.code != "Ok") return@runCatching null
            val route0 = response.routes.firstOrNull() ?: return@runCatching null
            
            EtaResult(
                durationSeconds = route0.duration.toLong(),
                distanceMeters = route0.distance.toLong()
            )
        }.getOrNull()
    }
