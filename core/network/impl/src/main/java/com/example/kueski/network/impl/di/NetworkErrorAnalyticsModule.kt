package com.example.kueski.network.impl.di

import com.example.kueski.network.api.analytics.NetworkErrorAnalytics
import com.example.kueski.network.impl.analytics.NetworkErrorAnalyticsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkErrorAnalyticsModule {

    @Binds
    fun bindNetworkErrorAnalytics(
        networkErrorAnalytics: NetworkErrorAnalyticsImpl
    ): NetworkErrorAnalytics
}