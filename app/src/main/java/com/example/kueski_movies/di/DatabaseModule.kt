package com.example.kueski_movies.di

import android.content.Context
import com.example.kueski_movies.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Singleton
  @Provides
  fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.build(context)

  @Singleton
  @Provides
  fun provideMoviesDao(appDatabase: AppDatabase) = appDatabase.moviesDao()
}