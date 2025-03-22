package com.example.kueski_movies.di

import com.example.kueski_movies.BuildConfig
import com.example.kueski_movies.data.remote.api.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
  @Provides
  @Singleton
  fun providesRetrofit(): Retrofit {
    val client = OkHttpClient.Builder()
      .readTimeout(20, TimeUnit.SECONDS)
      .connectTimeout(20, TimeUnit.SECONDS)
      .addInterceptor(HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) { level = HttpLoggingInterceptor.Level.BODY}
      })
    return Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl(BuildConfig.BASE_URL)
      .client(client.build())
      .build()
  }

  @Provides
  @Singleton
  fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(
    MoviesApi::class.java)
}