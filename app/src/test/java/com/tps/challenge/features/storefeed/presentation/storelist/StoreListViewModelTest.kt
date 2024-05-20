package com.tps.challenge.features.storefeed.presentation.storelist

import androidx.lifecycle.SavedStateHandle
import com.tps.challenge.Constants
import com.tps.challenge.core.domain.remote.NetworkHelper
import com.tps.challenge.core.domain.usecase.LocationUseCases
import com.tps.challenge.core.domain.utils.DispatcherProvider
import com.tps.challenge.core.domain.utils.Logger
import com.tps.challenge.core.presentation.base.UiText
import com.tps.challenge.features.storefeed.domain.model.StoreItem
import com.tps.challenge.features.storefeed.domain.usecase.StoreListUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.tps.challenge.R
import com.tps.challenge.features.storefeed.domain.usecase.GetStoreList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoreListViewModelTest {

    // Use a TestCoroutineDispatcher to control coroutine timing
    private val testDispatcher = TestCoroutineDispatcher()

    // Mock dependencies
    @Mock
    private lateinit var mockStoreListUseCases: StoreListUseCases
    @Mock
    private lateinit var mockLocationUseCases: LocationUseCases
    @Mock
    private lateinit var mockLogger: Logger
    @Mock
    private lateinit var mockNetworkHelper: NetworkHelper

    // The ViewModel we are testing
    private lateinit var viewModel: StoreListViewModel

    @Before
    fun setUp() {
        // Set the main dispatcher to the test dispatcher
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to the original Main dispatcher
        kotlinx.coroutines.Dispatchers.resetMain()
        // Cleanup the test coroutines
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchStores updates state correctly on successful fetch`() = runBlocking {
        // Mock directly GetStoreList
        val mockGetStoreList = mock(GetStoreList::class.java)

        viewModel = StoreListViewModel(
            storeListUseCases = StoreListUseCases(mockGetStoreList),
            locationUseCases = mockLocationUseCases,
            logger = mockLogger,
            dispatcherProvider = object : DispatcherProvider {
                override val io: CoroutineDispatcher
                    get() = testDispatcher
                override val default: CoroutineDispatcher
                    get() = testDispatcher
                override val main: CoroutineDispatcher
                    get() = testDispatcher
            },
            networkHelper = mockNetworkHelper,
            savedStateHandle = SavedStateHandle()
        )

        val mockStores = listOf(
            StoreItem("1", "Store One", "Description", "http://example.com/image1.png", "open", "100"),
            StoreItem("2", "Store Two", "Description", "http://example.com/image2.png", "closed", "200")
        )

        `when`(mockGetStoreList.invoke(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE))
            .thenReturn(Result.success(mockStores))
        `when`(mockNetworkHelper.isNetworkConnected()).thenReturn(true)

        viewModel.fetchStores(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)

        assertEquals(mockStores, viewModel.storeListUiState.value.storeList)
        assertFalse(viewModel.storeListUiState.value.isLoading)
    }

    @Test
    fun `fetchStores updates state correctly on failure`() = runBlocking {
        val mockGetStoreList = mock(GetStoreList::class.java)
        viewModel = StoreListViewModel(
            storeListUseCases = StoreListUseCases(mockGetStoreList),
            locationUseCases = mockLocationUseCases,
            logger = mockLogger,
            dispatcherProvider = object : DispatcherProvider {
                override val io: CoroutineDispatcher
                    get() = testDispatcher
                override val default: CoroutineDispatcher
                    get() = testDispatcher
                override val main: CoroutineDispatcher
                    get() = testDispatcher
            },
            networkHelper = mockNetworkHelper,
            savedStateHandle = SavedStateHandle()
        )
        // Given
        val exception = RuntimeException("Network error")
        `when`(mockGetStoreList.invoke(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE))
            .thenReturn(Result.failure(exception))
        `when`(mockNetworkHelper.isNetworkConnected()).thenReturn(true)

        // Act
        viewModel.fetchStores(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)

        // Assert
        assertEquals(viewModel.storeListUiState.value.error, UiText.StaticString(R.string.something_went_wrong))
        assertFalse(viewModel.storeListUiState.value.isLoading)
    }

    @Test
    fun `fetchStores handles no network connection`() = runBlockingTest {
        viewModel = StoreListViewModel(
            storeListUseCases = mockStoreListUseCases,
            locationUseCases = mockLocationUseCases,
            logger = mockLogger,
            dispatcherProvider = object : DispatcherProvider {
                override val io: CoroutineDispatcher
                    get() = testDispatcher
                override val default: CoroutineDispatcher
                    get() = testDispatcher
                override val main: CoroutineDispatcher
                    get() = testDispatcher
            },
            networkHelper = mockNetworkHelper,
            savedStateHandle = SavedStateHandle()
        )
        // Given
        `when`(mockNetworkHelper.isNetworkConnected()).thenReturn(false)

        // Act
        viewModel.fetchStores(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)

        // Assert
        assertEquals(viewModel.storeListUiState.value.error, UiText.StaticString(R.string.no_internet_connection))
        assertFalse(viewModel.storeListUiState.value.isLoading)
    }
}