package com.tps.challenge.core.presentation.base

sealed interface UiEvent<out T> {
    data class Success<T>(val data: T) : UiEvent<T>
    data class Error(val message: UiText) : UiEvent<Nothing>
    object Loading : UiEvent<Nothing>
    data class ShowSnackbar(val message: UiText): UiEvent<Nothing>
}