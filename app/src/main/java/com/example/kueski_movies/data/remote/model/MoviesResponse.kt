package com.example.kueski_movies.data.remote.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
  @SerializedName("page") val page: Int,
  @SerializedName("results") val results: List<MovieResponse>,
  @SerializedName("total_pages") val totalPages: Int,
  @SerializedName("total_results") val totalResults: Int,
)