package com.tps.challenge.features.storefeed.presentation.storelist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.Constants
import com.tps.challenge.R
import com.tps.challenge.core.domain.remote.NetworkHelper
import com.tps.challenge.core.domain.usecase.LocationUseCases
import com.tps.challenge.core.domain.utils.DispatcherProvider
import com.tps.challenge.core.domain.utils.Logger
import com.tps.challenge.core.presentation.base.UiEvent
import com.tps.challenge.core.presentation.base.UiText
import com.tps.challenge.features.storefeed.domain.usecase.StoreListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreListViewModel @Inject constructor(
    private val storeListUseCases: StoreListUseCases,
    private val locationUseCases: LocationUseCases,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _storeListUiState = MutableStateFlow<StoreListState>(StoreListState(emptyList(), true))
    val storeListUiState: StateFlow<StoreListState> = _storeListUiState
    private var fetchStoresJob: Job? = null

    init {
        useDefaultLocation()
    }

    public fun useDefaultLocation() {
        viewModelScope.launch(dispatcherProvider.io) {
            logger.d(
                TAG,
                "Using default location: (${Constants.DEFAULT_LATITUDE}, ${Constants.DEFAULT_LONGITUDE})"
            )
            //locationUseCases.saveLocation(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)
            fetchStores(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)
        }
    }

//    fun onLocationPermissionResult(granted: Boolean) {
//        viewModelScope.launch(dispatcherProvider.io) {
//            if (granted) {
//                handleGrantedLocationPermission()
//            } else {
//                handleDeniedLocationPermission()
//            }
//        }
//    }

//    private suspend fun handleGrantedLocationPermission() {
//        if(savedStateHandle.keys().contains("LOCATION")) {
//            savedStateHandle.get<Location>("LOCATION")
//                ?.let { fetchStores(it.lat, it.long) }
//        }
//        locationUseCases.getLocation().fold(
//            onSuccess = { location ->
//                if (location != null) {
//                    logger.d(
//                        TAG,
//                        "Permission granted, using actual location: (${location.lat}, ${location.long})"
//                    )
//                    savedStateHandle.set("LOCATION", location)
//                    locationUseCases.saveLocation(location.lat, location.long)
//                    fetchStores(location.lat, location.long)
//                } else {
//                    logger.d(
//                        TAG,
//                        "Permission granted but location is null, using default location."
//                    )
//                    useDefaultLocation()
//                }
//            },
//            onFailure = { exception ->
//                logger.d(
//                    TAG,
//                    "Failed to get location after permission granted: ${exception.localizedMessage}"
//                )
//                useDefaultLocation()
//            }
//        )
//    }

//    fun fetchStoresUsingSavedOrDefaultLocation() {
//        if(savedStateHandle.keys().contains("LOCATION")) {
//            savedStateHandle.get<Location>("LOCATION")
//                ?.let { fetchStores(it.lat, it.long) }
//        }
//        viewModelScope.launch(dispatcherProvider.io) {
//            locationUseCases.getLocation().fold(
//                onSuccess = { location ->
//                    if (location != null) {
//                        fetchStores(location.lat, location.long)
//                    } else {
//                        logger.d(TAG, "No location found, using default location.")
//                        useDefaultLocation()
//                    }
//                },
//                onFailure = { exception ->
//                    logger.d(TAG, "Failed to get location: ${exception.localizedMessage}")
//                    useDefaultLocation()
//                }
//            )
//        }
//    }

//    private suspend fun handleDeniedLocationPermission() {
//        logger.d(TAG, "Location permission denied, using default location.")
//        locationUseCases.saveLocation(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)
//        fetchStores(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE)
//    }

    fun fetchStores(lat: Double, long: Double) {
        if (fetchStoresJob?.isActive == true) {
            logger.d(TAG, "Fetch stores job is already active. Ignoring new request.")
            return
        }
        fetchStoresJob = viewModelScope.launch(dispatcherProvider.io) {
            _storeListUiState.value = _storeListUiState.value.copy(isLoading = true)
            try {
                if (!networkHelper.isNetworkConnected()) {
                    _storeListUiState.value =
                        _storeListUiState.value.copy(isLoading = false, error = UiText.StaticString(R.string.no_internet_connection))
                    return@launch
                }

                val storeResult = storeListUseCases.getStoreList(lat, long)
                storeResult.onSuccess {
                    _storeListUiState.value = _storeListUiState.value.copy(isLoading = false, storeList = it)
                }.onFailure { exception ->
                    logger.d(TAG, "Failed to fetch stores: ${exception.localizedMessage}")
                    _storeListUiState.value = _storeListUiState.value.copy(isLoading = false, error = UiText.StaticString(R.string.something_went_wrong))
                }
            } catch (exception: Exception) {
                logger.d(TAG, "Exception in fetchStores: ${exception.localizedMessage}")
                _storeListUiState.value = _storeListUiState.value.copy(isLoading = false, error = UiText.StaticString(R.string.something_went_wrong))
            } finally {
                fetchStoresJob = null
            }
        }
    }

    companion object {
        private const val TAG = "StoreListViewModel"
    }
}