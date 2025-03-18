package com.example.kueski_movies.data.movies.remote.model

import com.google.gson.annotations.SerializedName

data class BelongsToCollectionResponse(
  @SerializedName("backdrop_path") val backdropPath: String?,
  @SerializedName("id") val id: Int,
  @SerializedName("name") val name: String,
  @SerializedName("poster_path") val posterPath: String,
)