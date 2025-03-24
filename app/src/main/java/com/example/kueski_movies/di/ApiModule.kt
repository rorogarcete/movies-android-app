package com.example.kueski_movies.di

import com.example.kueski.network.api.APIService
import com.example.kueski_movies.data.remote.api.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

  @Provides
  @Singleton
  @APIService
  fun providesMoviesApi(@APIService retrofit: Retrofit): MoviesApi =
    retrofit.create(MoviesApi::class.java)
}