package com.example.kueski_movies.data.movies.remote.model

import com.google.gson.annotations.SerializedName

data class ProductionCompanyResponse(
  @SerializedName("id") val id: Int,
  @SerializedName("logo_path") val logoPath: String?,
  @SerializedName("name") val name: String,
  @SerializedName("origin_country") val originCountry: String,
)