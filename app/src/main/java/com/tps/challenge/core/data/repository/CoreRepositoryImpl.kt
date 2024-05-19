package com.tps.challenge.core.data.repository

import android.util.Log
import com.tps.challenge.core.data.preferences.DefaultPreferences
import com.tps.challenge.core.domain.model.Location
import com.tps.challenge.core.domain.repository.CoreRepository

class CoreRepositoryImpl(
    private val preferences: DefaultPreferences
): CoreRepository {
    override suspend fun getLocation(): Result<Location?> {
        return try {
            val location = preferences.getLocation()
            Result.success(location)
        } catch (exception: Exception) {
            Log.e(CoreRepositoryImpl::class.java.name, exception.message.toString())
            Result.failure(exception)
        }
    }

    override suspend fun saveLocation(lat: Double, long: Double) {
        preferences.saveLocation(Location(lat, long))
    }
}