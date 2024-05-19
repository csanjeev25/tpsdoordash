package com.tps.challenge.features.storefeed.domain.di

import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository
import com.tps.challenge.features.storefeed.domain.usecase.GetStoreList
import com.tps.challenge.features.storefeed.domain.usecase.StoreListUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object StoreListDomainModule {

    @ViewModelScoped
    @Provides
    fun provideStoreListUseCases(
        repository: StoreListRepository
    ): StoreListUseCases {
        return StoreListUseCases(
            getStoreList = GetStoreList(repository)
        )
    }
}