package com.tps.challenge.features.storefeed.data.mapper

import com.tps.challenge.features.storefeed.data.remote.dto.StoreDetailsResponse
import com.tps.challenge.features.storefeed.domain.model.StoreDetails

fun StoreDetailsResponse.toStoreDetails(): StoreDetails? {
    return StoreDetails(
        id = id,
        name = name ?: return null,
        address = address?.printableAddress ?: "",
        imageUrl = coverImgUrl,
        deliveryEta = deliveryEta,
        description = description,
        fee = deliveryFeeCents,
        phoneNumber = phoneNumber,
        status = status,
        tags = tags
    )
}