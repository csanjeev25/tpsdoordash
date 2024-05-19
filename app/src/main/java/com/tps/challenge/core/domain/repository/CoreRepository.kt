package com.tps.challenge.core.domain.repository

import com.tps.challenge.core.domain.model.Location

interface CoreRepository {
    suspend fun getLocation(): Result<Location?>
    suspend fun saveLocation(lat: Double, long: Double)
}