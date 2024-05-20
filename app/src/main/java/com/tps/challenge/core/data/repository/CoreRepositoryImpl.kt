package com.tps.challenge.core.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tps.challenge.core.data.preferences.DefaultPreferences
import com.tps.challenge.core.domain.model.Location
import com.tps.challenge.core.domain.preferences.Preferences
import com.tps.challenge.core.domain.repository.CoreRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CoreRepositoryImpl(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val preferences: Preferences
): CoreRepository {
    override suspend fun getLocation(): Result<Location?> {
        return try {
            val location = preferences.getLocation()
            if (location != null) {
                Result.success(location)
            } else {
                getLastKnownLocation()
            }
        } catch (exception: Exception) {
            Log.e(CoreRepositoryImpl::class.java.name, exception.message.toString())
            Result.failure(exception)
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation(): Result<Location?> = suspendCancellableCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    continuation.resume(Result.success(Location(location.latitude, location.longitude)))
                } else {
                    continuation.resume(Result.success(null))
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    override suspend fun saveLocation(lat: Double, long: Double) {
        preferences.saveLocation(Location(lat, long))
    }
}