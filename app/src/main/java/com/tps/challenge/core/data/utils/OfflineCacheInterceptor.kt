package com.tps.challenge.core.data.utils

import android.util.Log
import com.tps.challenge.core.domain.remote.NetworkHelper
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor(
    private val networkHelper: NetworkHelper
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!networkHelper.isNetworkConnected()) {
            val cacheControl = CacheControl.Builder()
                .maxStale(12, TimeUnit.HOURS)
                .build()
            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
        }
        return chain.proceed(request)
    }
}