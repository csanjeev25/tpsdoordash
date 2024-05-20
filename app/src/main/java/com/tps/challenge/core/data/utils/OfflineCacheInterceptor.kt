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
        val builder = request.newBuilder()
        if (!networkHelper.isNetworkConnected()) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        return chain.proceed(builder.build())
    }
}