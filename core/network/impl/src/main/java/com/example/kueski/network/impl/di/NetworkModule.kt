package com.example.kueski.network.impl.di

import com.example.kueski.network.api.APIService
import com.example.kueski.network.api.AppLoggingInterceptors
import com.example.kueski.network.api.AuthorizationInterceptors
import com.example.kueski.network.api.GeneralInterceptors
import com.example.kueski.network.api.interceptors.ApiServicesUrlInterceptor
import com.example.kueski.network.api.interceptors.AuthorizationInterceptor
import com.example.kueski.network.api.interceptors.ConnectionTimeOutInterceptor
import com.example.kueski.network.api.interceptors.HttpRetryInterceptor
import com.example.kueski.network.api.preferences.NetworkPreferences
import com.example.kueski.network.impl.constants.HttpConstants.AUTHORIZATION
import com.example.kueski.network.impl.constants.HttpConstants.BEARER
import com.example.kueski.network.impl.constants.HttpConstants.TIMEOUT
import com.example.kueski.network.impl.factories.ResultCallAdapterFactory
import com.example.kueski.network_impl.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(
    includes =
        [
            InterceptorModule::class,
            NetworkErrorAnalyticsModule::class,
            NetworkPreferenceModule::class,
            ClientsModule::class
        ]
)
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttp() = OkHttpClient.Builder().apply {
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
    }

    @Provides
    @Singleton
    fun provideAuthenticator(
        networkPreferences: NetworkPreferences
    ) = Authenticator { _, response ->
        response.request
            .newBuilder()
            .header(
                AUTHORIZATION,
                "$BEARER ${networkPreferences.accessToken()}"
            )
            .build()
    }

    @Provides
    @Singleton
    @AppLoggingInterceptors
    fun provideAppLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Provides
    @Singleton
    @GeneralInterceptors
    fun provideGeneralInterceptors(
        httpRetryInterceptor: HttpRetryInterceptor,
        connectionTimeOutInterceptor: ConnectionTimeOutInterceptor,
        @AppLoggingInterceptors loggingInterceptor: Interceptor
    ) = arrayListOf(
        connectionTimeOutInterceptor,
        httpRetryInterceptor,
        loggingInterceptor
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
    fun provideAPIServiceOkhttp(
        httpClientBuilder: OkHttpClient.Builder,
        tokenAuthenticator: Authenticator,
        @AuthorizationInterceptors authInterceptors: ArrayList<Interceptor>,
        microServicesUrlInterceptor: ApiServicesUrlInterceptor
    ): OkHttpClient {
        httpClientBuilder.authenticator(tokenAuthenticator)
        httpClientBuilder.addInterceptor(microServicesUrlInterceptor)
        authInterceptors.forEach { httpClientBuilder.addInterceptor(it) }

        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    @APIService
    fun provideAPIServiceClient(
        @APIService httpClient: Lazy<OkHttpClient>,
        gson: Gson,
        networkPreferences: NetworkPreferences,
        resultAdapterFactory: ResultCallAdapterFactory,
    ) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(resultAdapterFactory)
        .baseUrl(networkPreferences.baseApiService())
        .client(httpClient.get())
        .build()
}