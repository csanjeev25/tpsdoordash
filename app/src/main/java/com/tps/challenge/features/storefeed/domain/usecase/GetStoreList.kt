package com.tps.challenge.features.storefeed.domain.usecase

import com.tps.challenge.Constants
import com.tps.challenge.features.storefeed.domain.model.StoreItem
import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository

class GetStoreList(
    private val storeListRepository: StoreListRepository
) {
    suspend operator fun invoke(
        lat: Double?,
        long: Double?
    ): Result<List<StoreItem>> {
        val latitude = lat ?: Constants.DEFAULT_LATITUDE
        val longitude = long ?: Constants.DEFAULT_LONGITUDE

        if (latitude.isNaN() || longitude.isNaN()) {
            return Result.success(emptyList())
        }
        return storeListRepository.getStoreList(latitude, longitude)
    }
}