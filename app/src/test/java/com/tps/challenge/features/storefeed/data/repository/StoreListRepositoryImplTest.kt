package com.tps.challenge.features.storefeed.data.repository

import com.tps.challenge.features.storefeed.data.remote.StoreApiService
import com.tps.challenge.features.storefeed.data.remote.dto.StoreResponse
import com.tps.challenge.features.storefeed.domain.model.StoreItem
import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StoreListRepositoryImplTest {
    @Mock
    private lateinit var mockApiService: StoreApiService

    private lateinit var storeListRepository: StoreListRepository

    @Before
    fun setUp() {
        storeListRepository = StoreListRepositoryImpl(mockApiService)
    }

    @Test
    fun `getStoreList returns list of StoreItem on successful API call`() = runBlocking {
        // Given
        val latitude = 37.7749
        val longitude = -122.4194
        val mockStores = listOf(
            StoreResponse("1443", "Tommy Thai", "Good for Groups, Asian Food, Cambodian, Asian, Thai, Chinese", "https://cdn.doordash.com/media/restaurant/cover/Tommy-Thai.png", "Closed", "${799}"),
            StoreResponse("886348", "Taqueria El Grullense M&G", "Mexican", "https://cdn.doordash.com/media/restaurant/cover/Taqueria_El_Grullense_MG_Palo_Alto.png", "Opened", "${100}")
        )

        val expectedStoreItems = listOf(
            StoreItem("1443", "Tommy Thai", "Good for Groups, Asian Food, Cambodian, Asian, Thai, Chinese", "https://cdn.doordash.com/media/restaurant/cover/Tommy-Thai.png", "Closed", "${799}"),
            StoreItem("886348", "Taqueria El Grullense M&G", "Mexican", "https://cdn.doordash.com/media/restaurant/cover/Taqueria_El_Grullense_MG_Palo_Alto.png", "Opened", "${100}")
        )

        // When
        `when`(mockApiService.getStoreFeed(latitude, longitude)).thenReturn(mockStores)

        // Invoke
        val result = storeListRepository.getStoreList(latitude, longitude)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedStoreItems, result.getOrNull())
    }

    @Test
    fun `getStoreList handles exceptions thrown by API service`() = runBlocking {
        // Given
        val latitude = 37.7749
        val longitude = -122.4194
        val exception = RuntimeException("Network error")

        // When
        `when`(mockApiService.getStoreFeed(latitude, longitude)).thenThrow(exception)

        // Invoke
        val result = storeListRepository.getStoreList(latitude, longitude)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getStoreList remove empty name items`() = runBlocking {
        // Given
        val latitude = 37.7749
        val longitude = -122.4194
        val mockStores = listOf(
            StoreResponse("1443", "Tommy Thai", "Good for Groups, Asian Food, Cambodian, Asian, Thai, Chinese", "https://cdn.doordash.com/media/restaurant/cover/Tommy-Thai.png", "Closed", "${799}"),
            StoreResponse("886348", "Taqueria El Grullense M&G", "Mexican", "https://cdn.doordash.com/media/restaurant/cover/Taqueria_El_Grullense_MG_Palo_Alto.png", "Opened", "${100}"),
            StoreResponse("886348", "", "Mexican", "https://cdn.doordash.com/media/restaurant/cover/Taqueria_El_Grullense_MG_Palo_Alto.png", "Opened", "${100}")
        )

        val expectedStoreItems = listOf(
            StoreItem("1443", "Tommy Thai", "Good for Groups, Asian Food, Cambodian, Asian, Thai, Chinese", "https://cdn.doordash.com/media/restaurant/cover/Tommy-Thai.png", "Closed", "${799}"),
            StoreItem("886348", "Taqueria El Grullense M&G", "Mexican", "https://cdn.doordash.com/media/restaurant/cover/Taqueria_El_Grullense_MG_Palo_Alto.png", "Opened", "${100}")
        )

        // When
        `when`(mockApiService.getStoreFeed(latitude, longitude)).thenReturn(mockStores)

        // Invoke
        val result = storeListRepository.getStoreList(latitude, longitude)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedStoreItems, result.getOrNull())
    }

}