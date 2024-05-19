package com.tps.challenge.core.domain.remote

interface NetworkService {
    fun <T> create(service: Class<T>): T
}