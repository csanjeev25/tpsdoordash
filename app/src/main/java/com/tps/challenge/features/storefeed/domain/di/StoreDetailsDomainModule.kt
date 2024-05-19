package com.tps.challenge.features.storefeed.domain.di

import com.tps.challenge.features.storefeed.domain.repository.StoreDetailsRepository
import com.tps.challenge.features.storefeed.domain.usecase.GetStoreDetails
import com.tps.challenge.features.storefeed.domain.usecase.StoreDetailsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object StoreDetailsDomainModule {

    @Provides
    @ViewModelScoped
    fun provideStoreDetailsUseCase(repository: StoreDetailsRepository): StoreDetailsUseCases {
        return StoreDetailsUseCases(
            getStoreDetails = GetStoreDetails(repository)
        )
    }
}