package com.example.kueski_movies.di

import com.example.kueski.feature_flag.impl.di.RemoteConfigDefaultsModule
import com.example.kueski.feature_flag.impl.di.RemoteConfigModule
import com.example.kueski.logger_impl.di.LoggerModule
import com.example.kueski.network.impl.di.NetworkModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        LoggerModule::class,
        RemoteConfigModule::class,
        RemoteConfigDefaultsModule::class,
        NetworkModule::class
    ]
)
@InstallIn(SingletonComponent::class)
interface CoreModule