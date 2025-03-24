package com.example.kueski_movies.data.repositories

import androidx.room.withTransaction
import com.example.kueski.network.api.APIService
import com.example.kueski_movies.data.local.dao.MoviesDao
import com.example.kueski_movies.data.local.db.AppDatabase
import com.example.kueski_movies.data.mappers.MoviesDataMapper
import com.example.kueski_movies.data.remote.api.MoviesApi
import com.example.kueski_movies.data.remote.model.MoviesResponse
import javax.inject.Inject

class MovieRepositoryImpl  @Inject constructor(
    @APIService private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val mapper: MoviesDataMapper,
    private val appDb: AppDatabase
) : MovieRepository {

    override suspend fun getMovies(page: Int): MoviesResponse {
        val response = moviesApi.getMovies(page = page)

        appDb.withTransaction {
            moviesDao.insertAll(
                response.results.map { movie ->
                    mapper.map(Pair(movie, page))
                }
            )
        }

        return response
    }


    override suspend fun getMovie(movieId: String) =
        moviesApi.getMovie(id = movieId)
}