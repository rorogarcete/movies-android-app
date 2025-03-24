package com.example.kueski.network.impl.di

import com.example.kueski.network.api.preferences.NetworkPreferences
import com.example.kueski.network.impl.preferences.NetworkPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkPreferenceModule {

    @Binds
    fun bindNetworkPreferences(
        networkPreferences: NetworkPreferencesImpl
    ): NetworkPreferences
}