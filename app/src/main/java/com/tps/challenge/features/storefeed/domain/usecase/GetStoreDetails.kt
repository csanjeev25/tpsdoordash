package com.tps.challenge.features.storefeed.domain.usecase

import com.tps.challenge.features.storefeed.domain.model.StoreDetails
import com.tps.challenge.features.storefeed.domain.repository.StoreDetailsRepository
import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository

class GetStoreDetails(
    private val repository: StoreDetailsRepository
) {
    suspend operator fun invoke(
        id: String?
    ): Result<StoreDetails?> {
        if (id.isNullOrBlank()) {
            return Result.success(null)
        }
        return repository.getStoreDetails(id.trim())
    }
}