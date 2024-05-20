package com.tps.challenge.core.data.utils

import okhttp3.Interceptor
import okhttp3.Response

class NetworkCacheInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val maxAge = 60
        return response.newBuilder()
            .header("Cache-Control", "public, max-age=${maxAge}")
            .build()
    }
}