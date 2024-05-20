package com.tps.challenge.features.storefeed.domain.usecase

import com.nhaarman.mockitokotlin2.whenever
import com.tps.challenge.features.storefeed.domain.model.StoreItem
import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetStoreListTest {

    @Mock
    private lateinit var mockStoreListRepository: StoreListRepository

    private lateinit var getStoreList: GetStoreList
    @Before
    fun setUp() {
        getStoreList = GetStoreList(mockStoreListRepository)
    }

    @Test
    fun `invoke with valid coordinates returns correct store list`() = runBlocking {
        val lat = 37.779
        val long = -122.4194
        val mockStores = listOf(
            StoreItem("1443", "Tommy Thai", "Good for Groups, Asian Food, Cambodian, Asian, Thai, Chinese", "https://cdn.doordash.com/media/restaurant/cover/Tommy-Thai.png", "Closed", "${799}"),
            StoreItem("886348", "Taqueria El Grullense M&G", "Mexican", "https://cdn.doordash.com/media/restaurant/cover/Taqueria_El_Grullense_MG_Palo_Alto.png", "Opened", "${100}")
        )

        whenever(mockStoreListRepository.getStoreList(lat, long)).thenReturn(Result.success(mockStores))

        val result = getStoreList(lat, long)

        assertEquals(result.getOrNull(), mockStores)
    }
}