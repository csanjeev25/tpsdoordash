package com.tps.challenge.features.storefeed.domain.repository

import com.tps.challenge.features.storefeed.domain.model.StoreDetails

interface StoreDetailsRepository {
    suspend fun getStoreDetails(id: String): Result<StoreDetails?>
}