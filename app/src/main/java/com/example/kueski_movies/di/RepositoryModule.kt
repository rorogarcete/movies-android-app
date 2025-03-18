package com.example.kueski_movies.di

import com.example.kueski_movies.data.movies.repository.DefaultMoviesRepository
import com.example.kueski_movies.data.movies.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Singleton
  @Binds
  abstract fun providesMoviesRepository(repository: DefaultMoviesRepository): MoviesRepository
}