package com.example.kueski_movies.data.movies.remote.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(
  @SerializedName("id") val id: Int,
  @SerializedName("name") val name: String,
)