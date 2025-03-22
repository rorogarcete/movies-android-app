package com.example.kueski_movies.data.repositories

import com.example.kueski_movies.data.remote.model.MovieDetailsResponse
import com.example.kueski_movies.data.remote.model.MoviesResponse

interface MovieRepository {

    suspend fun getMovies(page: Int): MoviesResponse

    suspend fun getMovie(movieId: String): MovieDetailsResponse
}