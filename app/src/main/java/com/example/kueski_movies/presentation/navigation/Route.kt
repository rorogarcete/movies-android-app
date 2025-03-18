package com.example.kueski_movies.presentation.navigation

import android.os.Bundle

sealed class Route(val route: String) {
  data object ExploreMovies : Route("ExploreMovies")
  data object MovieDetails : Route("MovieDetails/{movieId}") {
    const val ID: String = "movieId"

    fun getRoute(movieId: Int) = "MovieDetails/$movieId"

    fun getId(bundle: Bundle?): Int {
      val movieId = bundle?.getInt(ID)
      requireNotNull(movieId) { "The movieId was not provided" }
      return movieId
    }
  }
}