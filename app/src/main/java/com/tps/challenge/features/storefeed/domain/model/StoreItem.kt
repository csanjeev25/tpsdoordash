package com.tps.challenge.features.storefeed.domain.model

data class StoreItem (
    val id: String,
    val name: String,
    val description: String,
    val coverImgUrl: String,
    val status: String,
    val deliveryFee: String
)