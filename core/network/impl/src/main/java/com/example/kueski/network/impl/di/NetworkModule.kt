package com.example.kueski.network.impl.di

import com.example.kueski.network.api.AuthorizationInterceptors
import com.example.kueski.network.api.GeneralInterceptors
import com.example.kueski.network.api.APIService
import com.example.kueski.network.api.interceptors.AuthorizationInterceptor
import com.example.kueski.network.api.interceptors.ConnectionTimeOutInterceptor
import com.example.kueski.network.api.interceptors.HttpRetryInterceptor
import com.example.kueski.network.api.interceptors.MicroServicesUrlInterceptor
import com.example.kueski.network.api.preferences.NetworkPreferences
import com.example.kueski.network.impl.factories.ResultCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(
    includes =
        [
            InterceptorModule::class,
            NetworkAnalyticsModule::class,
            NetworkPreferenceModule::class,
        ]
)
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    @GeneralInterceptors
    fun provideGeneralInterceptors(
        httpRetryInterceptor: HttpRetryInterceptor,
        connectionTimeOutInterceptor: ConnectionTimeOutInterceptor,
        microServicesUrlInterceptor: MicroServicesUrlInterceptor
    ) = arrayListOf(
        connectionTimeOutInterceptor,
        httpRetryInterceptor,
        microServicesUrlInterceptor
    )

    @Provides
    @Singleton
    @AuthorizationInterceptors
    fun provideAuthorizationInterceptors(
        @GeneralInterceptors generalInterceptors: ArrayList<Interceptor>,
        authorizationInterceptor: AuthorizationInterceptor
    ): ArrayList<Interceptor> {
        val authorizationArray: ArrayList<Interceptor> = ArrayList()
        authorizationArray.add(authorizationInterceptor)
        generalInterceptors.forEach { authorizationArray.add(it) }

        return authorizationArray
    }

    @Provides
    @Singleton
    @APIService
    fun provideMicroServiceOkhttp(
        httpClientBuilder: OkHttpClient.Builder,
        tokenAuthenticator: Authenticator,
        @AuthorizationInterceptors authInterceptors: ArrayList<Interceptor>,
        microServicesUrlInterceptor: MicroServicesUrlInterceptor
    ): OkHttpClient {
        httpClientBuilder.authenticator(tokenAuthenticator)
        httpClientBuilder.addInterceptor(microServicesUrlInterceptor)
        authInterceptors.forEach { httpClientBuilder.addInterceptor(it) }

        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    @APIService
    fun provideMicroServiceClient(
        @APIService httpClient: OkHttpClient,
        gson: Gson,
        networkPreferences: NetworkPreferences,
        resultAdapterFactory: ResultCallAdapterFactory,
    ) = provideRetrofitBuilder(
        httpClient,
        networkPreferences.baseMicroService(),
        gson,
        resultAdapterFactory
    ).build()

    @Provides
    fun provideRetrofitBuilder(
        httpClient: OkHttpClient,
        path: String,
        gson: Gson,
        resultAdapterFactory: ResultCallAdapterFactory,
    ) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(resultAdapterFactory)
        .baseUrl(path)
        .client(httpClient)

}