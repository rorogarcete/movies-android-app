package com.example.kueski_movies.di

import com.example.kueski.feature_flag.api.RemoteConfigDefaults
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {

    @IntoMap
    @Provides
    @RemoteConfigDefaults
    @StringKey(ENABLE_FIREBASE)
    fun enableFirebaseDefault() =  true

    @IntoMap
    @Provides
    @RemoteConfigDefaults
    @StringKey(ENABLE_FAVOURITE_FEATURE)
    fun enableFacebookDefault() = true

    // Feature flags
    private const val ENABLE_FAVOURITE_FEATURE = "enable_favourite_feature"
    private const val ENABLE_FIREBASE = "enable_firebase_analytics"
}