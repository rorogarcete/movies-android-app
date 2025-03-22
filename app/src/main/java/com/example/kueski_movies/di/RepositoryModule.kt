package com.example.kueski_movies.di

import com.example.kueski_movies.data.repositories.MovieRepositoryImpl
import com.example.kueski_movies.data.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

  @Binds
  fun bindsRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}