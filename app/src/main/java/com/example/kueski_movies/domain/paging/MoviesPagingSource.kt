package com.example.kueski_movies.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kueski.logger_api.Logger
import com.example.kueski_movies.data.repositories.MovieRepository
import com.example.kueski_movies.domain.mappers.MovieMapper
import com.example.kueski_movies.domain.models.Movie
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
  private val movieRepository: MovieRepository,
  private val movieMapper: MovieMapper,
  private val logger: Logger
) : PagingSource<Int, Movie>() {

  override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
    try {
      val nextPageNumber = params.key ?: 1
      val response = movieRepository.getMovies(page = nextPageNumber)

      val movies = response.results.map { movie ->
        movieMapper.map(movie)
      }

      return LoadResult.Page(movies, null, response.page + 1)
    } catch (e: Exception) {
      logger.e(
        tag = MoviesPagingSource::class.java.simpleName,
        msg = e.message,
        throwable = e.cause
      )

      return LoadResult.Error(e)
    }
  }
}