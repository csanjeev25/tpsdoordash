package com.tps.challenge.features.storefeed.data.repository

import com.tps.challenge.features.storefeed.data.mapper.toStoreDetails
import com.tps.challenge.features.storefeed.data.mapper.toStoreList
import com.tps.challenge.features.storefeed.data.remote.StoreApiService
import com.tps.challenge.features.storefeed.domain.model.StoreDetails
import com.tps.challenge.features.storefeed.domain.model.StoreItem
import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository

class StoreListRepositoryImpl(
    private val apiService: StoreApiService
): StoreListRepository {
    override suspend fun getStoreList(lat: Double, long: Double): Result<List<StoreItem>> {
        return try {
            val storeList = apiService.getStoreFeed(lat, long)
            Result.success(storeList.mapNotNull {
                it.toStoreList()
            })
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}