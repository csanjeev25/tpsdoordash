package com.tps.challenge.core.data.remote

import com.tps.challenge.core.domain.remote.NetworkService
import retrofit2.Retrofit

class RetrofitNetworkService(private val retrofit: Retrofit) : NetworkService {
    override fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}
