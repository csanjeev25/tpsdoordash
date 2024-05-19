package com.tps.challenge.core.domain.usecase

import com.tps.challenge.core.domain.repository.CoreRepository

class SaveLocation(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(
        lat: Double,
        long: Double
    ) {
        if (lat.isNaN() or long.isNaN()) return
        repository.saveLocation(lat, long)
    }
}