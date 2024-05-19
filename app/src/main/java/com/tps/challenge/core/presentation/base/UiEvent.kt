package com.tps.challenge.core.presentation.base

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: UiText) : UiState<Nothing>
    object Loading : UiState<Nothing>

    data class ShowSnackbar(val message: UiText): UiState<Nothing>
}