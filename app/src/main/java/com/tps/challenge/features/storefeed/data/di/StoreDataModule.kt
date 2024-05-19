package com.tps.challenge.features.storefeed.data.di

import com.tps.challenge.core.domain.remote.NetworkService
import com.tps.challenge.features.storefeed.data.remote.StoreApiService
import com.tps.challenge.features.storefeed.data.repository.StoreDetailsRepositoryImpl
import com.tps.challenge.features.storefeed.data.repository.StoreListRepositoryImpl
import com.tps.challenge.features.storefeed.domain.repository.StoreDetailsRepository
import com.tps.challenge.features.storefeed.domain.repository.StoreListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object StoreDataModule {
    @Provides
    @ViewScoped
    fun provideStoreApiService(networkService: NetworkService): StoreApiService = networkService.create(StoreApiService::class.java)

    @Provides
    @ViewScoped
    fun provideStoreListRepository(storeApiService: StoreApiService): StoreListRepository {
        return StoreListRepositoryImpl(storeApiService)
    }

    @Provides
    @ViewScoped
    fun provideStoreDetailsRepository(storeApiService: StoreApiService): StoreDetailsRepository {
        return StoreDetailsRepositoryImpl(storeApiService)
    }
}