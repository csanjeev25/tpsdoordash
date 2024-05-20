package com.tps.challenge.core.data.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tps.challenge.core.data.remote.RetrofitNetworkService
import com.tps.challenge.core.data.utils.NetworkCacheInterceptor
import com.tps.challenge.core.data.utils.OfflineCacheInterceptor
import com.tps.challenge.core.domain.remote.NetworkHelper
import com.tps.challenge.core.domain.remote.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Provides Network communication related instances.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://dd-interview.github.io/android/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    @Provides
    @Singleton
    fun provideOfflineCacheInterceptor(networkHelper: NetworkHelper): OfflineCacheInterceptor {
        return OfflineCacheInterceptor(networkHelper)
    }

    @Provides
    @Singleton
    fun provideNetworkCacheInterceptor(): NetworkCacheInterceptor {
        return NetworkCacheInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(offlineCacheInterceptor: OfflineCacheInterceptor, networkCacheInterceptor: NetworkCacheInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(offlineCacheInterceptor)
            .addNetworkInterceptor(networkCacheInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return RetrofitNetworkService(retrofit)
    }
}
