package com.example.kueski.network.impl.di

import android.content.Context
import com.amplitude.api.Amplitude
import com.amplitude.api.AmplitudeClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ClientsModule {

    @Provides
    @Singleton
    fun providesAmplitudeClient(
        @ApplicationContext context: Context,
        @Named("AMPLITUDE_KEY") apiKey: String
    ): AmplitudeClient {
        val client = Amplitude.getInstance()
        client.initialize(context, apiKey)
        return client
    }

    @Provides
    @Named("AMPLITUDE_KEY")
    fun providesAmplitudeApiKey() = "you_amplitude_api_key"
}