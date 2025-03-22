package com.example.kueski_movies.data.repositories

import com.example.kueski_movies.data.local.dao.MoviesDao
import com.example.kueski_movies.data.mappers.MoviesDataMapper
import com.example.kueski_movies.data.remote.api.MoviesApi
import com.example.kueski_movies.data.remote.model.MoviesResponse
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl  @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val mapper: MoviesDataMapper
) : MovieRepository {

    override suspend fun getMovies(page: Int): MoviesResponse {
        val response = moviesApi.getMovies(page = page)

        withContext(Dispatchers.IO) {
            moviesDao.insertAll(
                response.results.map { movie ->
                    mapper.map(Pair(movie, page))
                }
            )
        }

        // Get movies from local db
        // val movies = moviesDao.getMovies()

        return response
    }


    override suspend fun getMovie(movieId: String) =
        moviesApi.getMovie(id = movieId)
}