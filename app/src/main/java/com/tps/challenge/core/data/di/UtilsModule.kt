package com.tps.challenge.core.data.di

import android.content.Context
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
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
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
}