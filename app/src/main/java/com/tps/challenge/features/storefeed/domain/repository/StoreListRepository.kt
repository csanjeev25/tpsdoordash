package com.tps.challenge.features.storefeed.domain.repository

import com.tps.challenge.features.storefeed.domain.model.StoreItem

interface StoreListRepository {
    suspend fun getStoreList(
        lat: Double,
        long: Double
    ): Result<List<StoreItem>>
}