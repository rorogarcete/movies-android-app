package com.example.kueski_movies.data.movies.repository

import com.example.kueski_movies.data.movies.remote.model.MovieDetailsResponse
import com.example.kueski_movies.data.movies.remote.model.MoviesResponse

interface MoviesRepository {
  suspend fun getRecentMovies(): MoviesResponse
  suspend fun getMovie(id: Int): MovieDetailsResponse?
}