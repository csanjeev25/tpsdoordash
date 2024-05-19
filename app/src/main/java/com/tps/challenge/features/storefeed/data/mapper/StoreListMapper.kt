package com.tps.challenge.features.storefeed.data.mapper

import com.tps.challenge.features.storefeed.data.remote.dto.StoreResponse
import com.tps.challenge.features.storefeed.domain.model.StoreItem

fun StoreResponse.toStoreList(): StoreItem? {
    return StoreItem(
        id = id,
        name = name ?: return null,
        description = description,
        coverImgUrl = coverImgUrl,
        status = status,
        deliveryFee = deliveryFeeCents
    )
}