package com.tps.challenge.core.domain.usecase

import com.tps.challenge.core.domain.model.Location
import com.tps.challenge.core.domain.repository.CoreRepository

class GetLocation(
    private val coreRepository: CoreRepository
) {
    suspend operator fun invoke(): Result<Location?> {
        return coreRepository.getLocation()
    }
}