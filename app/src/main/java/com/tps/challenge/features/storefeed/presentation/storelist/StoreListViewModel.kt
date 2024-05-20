package com.tps.challenge.features.storefeed.presentation.storelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tps.challenge.R
import com.tps.challenge.core.domain.model.Location
import com.tps.challenge.core.domain.remote.NetworkHelper
import com.tps.challenge.core.domain.usecase.LocationUseCases
import com.tps.challenge.core.domain.utils.DispatcherProvider
import com.tps.challenge.core.domain.utils.Logger
import com.tps.challenge.core.presentation.base.UiState
import com.tps.challenge.core.presentation.base.UiText
import com.tps.challenge.features.storefeed.domain.usecase.StoreListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StoreListViewModel @Inject constructor(
    private val storeListUseCase: StoreListUseCases,
    private val locationUseCases: LocationUseCases,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _storeListUiState = MutableStateFlow<UiState<StoreListState>>(UiState.Loading)
    val storeListUiState: StateFlow<UiState<StoreListState>> = _storeListUiState

    private var location: Location? = null

    private fun checkIfInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    init {
        fetchStores()
    }

    fun fetchStores() {
        viewModelScope.launch {
            _storeListUiState.value = UiState.Loading
            try {
                val storeResult = withContext(dispatcherProvider.io) {
                    val isConnected = withContext(dispatcherProvider.default) {
                        checkIfInternetConnection()
                    }
                    if (!isConnected) _storeListUiState.value = UiState.Error(
                        UiText.StaticString(
                            R.string.no_internet_connection
                        )
                    )
                    if (location == null) {
                        location = locationUseCases.getLocation()
                            .getOrNull()
                    }
                    storeListUseCase.getStoreList(
                        location?.lat,
                        location?.long
                    )
                }
                storeResult
                    .onSuccess {
                        _storeListUiState.value = UiState.Success(StoreListState(it))
                    }
                    .onFailure {
                        _storeListUiState.value =
                            UiState.Error(UiText.StaticString(R.string.something_went_wrong))
                    }
            } catch (exception: Exception) {
                _storeListUiState.value =
                    UiState.Error(UiText.StaticString(R.string.something_went_wrong))
                logger.d(StoreListViewModel::class.java.name, exception.localizedMessage)
            }
        }
    }
}