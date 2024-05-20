package com.tps.challenge.features.storefeed.domain.usecase

import com.tps.challenge.features.storefeed.domain.model.StoreItem
import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository

class GetStoreList(
    private val storeListRepository: StoreListRepository
) {
    suspend operator fun invoke(
        lat: Double?,
        long: Double?
    ): Result<List<StoreItem>> {
        val latitude = lat ?: DEFAULT_LATITUDE
        val longitude = long ?: DEFAULT_LONGITUDE

        if (latitude.isNaN() || longitude.isNaN()) {
            return Result.success(emptyList())
        }
        return storeListRepository.getStoreList(latitude, longitude)
    }

    companion object {
        const val DEFAULT_LATITUDE = 37.422740
        const val DEFAULT_LONGITUDE = -122.139956
    }
}