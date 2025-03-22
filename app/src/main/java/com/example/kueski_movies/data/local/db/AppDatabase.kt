package com.example.kueski_movies.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kueski_movies.data.local.converter.DbConverter
import com.example.kueski_movies.data.local.dao.MoviesDao
import com.example.kueski_movies.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(DbConverter::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun moviesDao(): MoviesDao

  companion object {
    fun build(context: Context) =
      Room.databaseBuilder(context, AppDatabase::class.java, "movies_database.db")
        .build()
  }
}