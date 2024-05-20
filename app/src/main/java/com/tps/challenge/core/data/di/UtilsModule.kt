package com.tps.challenge.core.data.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tps.challenge.core.data.utils.DefaultDispatcherProvider
import com.tps.challenge.core.data.remote.NetworkHelperImpl
import com.tps.challenge.core.data.utils.AppLogger
import com.tps.challenge.core.domain.utils.DispatcherProvider
import com.tps.challenge.core.domain.remote.NetworkHelper
import com.tps.challenge.core.domain.utils.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {
    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Singleton
    @Provides
    fun provideNetworkHelper(connectivityManager: ConnectivityManager): NetworkHelper {
        return NetworkHelperImpl(connectivityManager)
    }

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    @Singleton
    @Provides
    fun provideLogger(): Logger {
        return AppLogger()
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}