package com.tps.challenge.core.presentation.di

import android.content.Context
import com.tps.challenge.core.presentation.utils.GlideImageLoader
import com.tps.challenge.core.presentation.utils.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CorePresentationModule {
    @Singleton
    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return GlideImageLoader(context)
    }
}