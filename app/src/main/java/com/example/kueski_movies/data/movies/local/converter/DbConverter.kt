package com.example.kueski_movies.data.movies.local.converter

import androidx.room.TypeConverter

class DbConverter {
  @TypeConverter
  fun fromIntList(ids: List<Int>): String {
    return ids.joinToString(",")
  }

  @TypeConverter
  fun toIntList(idsString: String): List<Int> {
    return idsString.split(",").map { it.toInt() }
  }
}