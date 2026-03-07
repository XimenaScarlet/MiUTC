package com.example.univapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MapsApiService {
    @GET("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("waypoints") waypoints: String?,
        @Query("mode") mode: String = "driving",
        @Query("units") units: String = "metric",
        @Query("key") apiKey: String
    ): DirectionsResponse

    @GET(".")
    suspend fun getOsrmRoute(@Url url: String): OsrmResponse

    @GET
    suspend fun getOsrmDirections(@Url url: String): OsrmFullResponse
}

data class DirectionsResponse(
    val status: String,
    val routes: List<GoogleRoute>
)

data class GoogleRoute(
    val overview_polyline: PolylinePoints
)

data class PolylinePoints(
    val points: String
)

data class OsrmResponse(
    val code: String,
    val routes: List<OsrmRoute>
)

data class OsrmRoute(
    val duration: Double,
    val distance: Double
)

data class OsrmFullResponse(
    val code: String,
    val message: String?,
    val routes: List<OsrmFullRoute>?
)

data class OsrmFullRoute(
    val geometry: String
)
