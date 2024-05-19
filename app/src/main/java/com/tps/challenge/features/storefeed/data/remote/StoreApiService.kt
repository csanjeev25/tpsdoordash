package com.tps.challenge.features.storefeed.data.remote

import com.tps.challenge.features.storefeed.data.remote.dto.StoreDetailsResponse
import com.tps.challenge.features.storefeed.data.remote.dto.StoreResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Communicates with the TPS Challenge backend to obtain data using the coroutines.
 */

interface StoreApiService {
    /**
     * Returns the Store feed per location provided.
     */
    @GET("v1/feed")
    suspend fun getStoreFeed(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double
    ): List<StoreResponse>

    /**
     * Returns a detailed specification for the Store.
     */
    @GET("v1/stores/{id}")
    suspend fun getStoreDetails(
        @Path("id") storeId: String
    ): StoreDetailsResponse
}
