package com.tps.challenge.core.data.di

import com.tps.challenge.core.data.preferences.DefaultPreferences
import com.tps.challenge.core.data.repository.CoreRepositoryImpl
import com.tps.challenge.core.domain.preferences.Preferences
import com.tps.challenge.core.domain.repository.CoreRepository
import com.tps.challenge.core.domain.usecase.GetLocation
import com.tps.challenge.core.domain.usecase.LocationUseCases
import com.tps.challenge.core.domain.usecase.SaveLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCoreRepository(preferences: DefaultPreferences): CoreRepository {
        return CoreRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideLocationUseCases(repository: CoreRepository): LocationUseCases {
        return LocationUseCases(
            getLocation = GetLocation(repository),
            saveLocation = SaveLocation(repository)
        )
    }
}