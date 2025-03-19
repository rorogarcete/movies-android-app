package com.example.kueski_movies.data.movies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kueski_movies.data.movies.remote.api.MoviesApi
import com.example.kueski_movies.data.movies.remote.model.MovieResponse
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
  private val moviesApi: MoviesApi,
) : PagingSource<Int, MovieResponse>() {

  override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
    try {
      val nextPageNumber = params.key ?: 1
      val response = moviesApi.getMovies(page = nextPageNumber)
      return LoadResult.Page(response.results, null, response.page + 1)
    } catch (e: Exception) {
      return LoadResult.Error(e)
    }
  }
}