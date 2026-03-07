package com.example.univapp.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class LocationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val fused by lazy { LocationServices.getFusedLocationProviderClient(context) }

    fun hasPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): LatLng? {
        if (!hasPermission()) return null

        val tokenSrc = CancellationTokenSource()
        val freshed = runCatching {
            fused.getCurrentLocation(
                CurrentLocationRequest.Builder()
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                    .build(),
                tokenSrc.token
            ).awaitNullable()
        }.getOrNull()
        if (freshed != null) return LatLng(freshed.latitude, freshed.longitude)

        val single = runCatching { requestSingleUpdate(8000L) }.getOrNull()
        if (single != null) return LatLng(single.latitude, single.longitude)

        val last = runCatching { fused.lastLocation.awaitNullable() }.getOrNull()
        return last?.let { LatLng(it.latitude, it.longitude) }
    }

    @SuppressLint("MissingPermission")
    private suspend fun requestSingleUpdate(timeoutMs: Long): android.location.Location? =
        suspendCancellableCoroutine { cont ->
            val req = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0L)
                .setWaitForAccurateLocation(true)
                .setMaxUpdates(1)
                .setDurationMillis(timeoutMs)
                .build()

            val cb = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    fused.removeLocationUpdates(this)
                    if (!cont.isCompleted) cont.resume(result.lastLocation)
                }
            }
            fused.requestLocationUpdates(req, cb, android.os.Looper.getMainLooper())
            cont.invokeOnCancellation { fused.removeLocationUpdates(cb) }
        }
}

private suspend fun <T> Task<T>.awaitNullable(): T? =
    suspendCancellableCoroutine { cont ->
        addOnSuccessListener { cont.resume(it) }
        addOnFailureListener { cont.resume(null) }
        addOnCanceledListener { if (!cont.isCompleted) cont.resume(null) }
    }
