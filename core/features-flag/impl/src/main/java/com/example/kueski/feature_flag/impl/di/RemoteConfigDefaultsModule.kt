package com.example.kueski.feature_flag.impl.di

import com.example.kueski.feature_flag.api.RemoteConfigDefaults
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigDefaultsModule {

    @IntoMap
    @Provides
    @RemoteConfigDefaults
    @StringKey(ENABLE_FIREBASE)
    fun enableFirebaseDefault() = true

    @IntoMap
    @Provides
    @RemoteConfigDefaults
    @StringKey(ENABLE_FAVORITE_FEATURE)
    fun enableFavoriteFeature() = true

    // Feature flags
    private const val ENABLE_FAVORITE_FEATURE = "enable_favorite_feature"
    private const val ENABLE_FIREBASE = "enable_firebase_analytics"
}