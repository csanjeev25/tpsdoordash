package com.tps.challenge.core.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tps.challenge.core.data.preferences.DefaultPreferences
import com.tps.challenge.core.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        application: Application
    ): SharedPreferences {
        return application.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences, gson: Gson): Preferences {
        return DefaultPreferences(sharedPreferences, gson)
    }
}