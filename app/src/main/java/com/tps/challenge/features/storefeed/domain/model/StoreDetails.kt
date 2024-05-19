package com.tps.challenge.features.storefeed.domain.model

data class StoreDetails(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val phoneNumber: String,
    val deliveryEta: String,
    val status: String,
    val fee: Int,
    val tags: List<String>,
    val address: String
)
