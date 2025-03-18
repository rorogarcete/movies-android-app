package com.example.kueski_movies.data.movies.remote.api

import com.example.kueski_movies.BuildConfig
import com.example.kueski_movies.data.movies.remote.model.MovieDetailsResponse
import com.example.kueski_movies.data.movies.remote.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

  @GET("3/discover/movie")
  suspend fun getMovies(
    @Query("include_adult") includeAdult: Boolean = false,
    @Query("include_video") includeVideo: Boolean = false,
    @Query("language") language: String = "en-US",
    @Query("page") page: Int?,
    @Query("sort_by") sortBy: String = "popularity.desc",
    @Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY,
  ): MoviesResponse

  @GET("3/movie/{movie_id}")
  suspend fun getMovie(
    @Path("movie_id") id: String,
    @Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY,
  ): MovieDetailsResponse?
}