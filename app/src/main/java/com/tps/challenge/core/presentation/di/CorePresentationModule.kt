package com.tps.challenge.core.presentation.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
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
    fun provideGlideRequestManager(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
    }

    @Singleton
    @Provides
    fun provideImageLoader(glideRequestManager: RequestManager): ImageLoader {
        return GlideImageLoader(glideRequestManager)
    }
}