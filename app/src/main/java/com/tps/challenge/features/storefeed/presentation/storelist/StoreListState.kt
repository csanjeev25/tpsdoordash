package com.tps.challenge.features.storefeed.presentation.storelist

import com.tps.challenge.core.presentation.base.UiText
import com.tps.challenge.features.storefeed.domain.model.StoreItem

data class StoreListState(
    val storeList: List<StoreItem>,
    val isLoading: Boolean = false,
    val error: UiText? = null
)