package com.example.kueski.logger_impl.di

import com.example.kueski.logger_api.Logger
import com.example.kueski.logger_impl.LogUtil
import com.example.kueski.logger_impl.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {

    companion object {
        @Provides
        fun provideLogUti() = LogUtil
    }

    @Binds
    fun bindsLogger(logger: LoggerImpl): Logger
}