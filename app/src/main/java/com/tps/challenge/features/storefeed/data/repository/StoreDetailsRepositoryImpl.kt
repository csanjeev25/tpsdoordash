package com.tps.challenge.features.storefeed.data.repository

import com.tps.challenge.features.storefeed.data.mapper.toStoreDetails
import com.tps.challenge.features.storefeed.data.remote.StoreApiService
import com.tps.challenge.features.storefeed.domain.model.StoreDetails
import com.tps.challenge.features.storefeed.domain.repository.StoreDetailsRepository

class StoreDetailsRepositoryImpl(
    private val apiService: StoreApiService
): StoreDetailsRepository {
    override suspend fun getStoreDetails(id: String): Result<StoreDetails?> {
        return try {
            val storeDetails = apiService.getStoreDetails(id)
            Result.success(storeDetails.toStoreDetails())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}