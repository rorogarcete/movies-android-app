package com.example.kueski_movies.presentation.screen.explore_movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kueski_movies.domain.paging.MoviesPagingSource
import com.example.kueski_movies.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class ExploreMoviesViewModel @Inject constructor(
  private val moviesPagingSource: MoviesPagingSource,
) : ViewModel() {

  fun getRecentMovies() : Flow<PagingData<Movie>>{
    return Pager(
      PagingConfig(pageSize = 20),
    ) {
      moviesPagingSource
    }.flow.cachedIn(viewModelScope)
  }
}