package com.tps.challenge.features.storefeed.presentation.storelist

sealed class StoreListEvent {
    object onLocationClick: StoreListEvent()
    object onItemClick: StoreListEvent()
}