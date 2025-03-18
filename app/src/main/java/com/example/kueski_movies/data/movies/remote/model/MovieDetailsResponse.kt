package com.example.kueski_movies.data.movies.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
  @SerializedName("id") val id: Int,
  @SerializedName("title") val title: String,
  @SerializedName("overview") val overview: String,
  @SerializedName("release_date") val releaseDate: String,
  @SerializedName("poster_path") val posterPath: String?,
  @SerializedName("vote_average") val voteAverage: Double,
  @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyResponse>,
  @SerializedName("production_countries") val productionCountries: List<ProductionCountryResponse>,
)