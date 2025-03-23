package com.example.kueski.feature_flag.impl.di

import com.example.kueski.feature_flag.api.RemoteConfigManager
import com.example.kueski.feature_flag.impl.RemoteConfigManagerImpl
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteConfigModule {

    companion object {
        @Provides
        fun providesRemoteConfig() = FirebaseRemoteConfig.getInstance()
    }

    @Binds
    fun bindsRemoteConfigManager(remoteConfigManager: RemoteConfigManagerImpl): RemoteConfigManager
}