package com.example.kueski_movies.data.movies.repository

import com.example.kueski_movies.data.movies.remote.api.MoviesApi
import com.example.kueski_movies.data.movies.remote.model.MovieDetailsResponse
import com.example.kueski_movies.data.movies.remote.model.MoviesResponse
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
  private val moviesApi: MoviesApi,
) : MoviesRepository {
  override suspend fun getRecentMovies(): MoviesResponse {
    return moviesApi.getMovies(page = 1)
  }

  override suspend fun getMovie(id: Int): MovieDetailsResponse? {
    return moviesApi.getMovie(id.toString())
  }
}