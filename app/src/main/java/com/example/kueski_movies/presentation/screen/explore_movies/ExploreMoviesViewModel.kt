package com.example.kueski_movies.presentation.screen.explore_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kueski_movies.domain.models.Movie
import com.example.kueski_movies.domain.paging.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class ExploreMoviesViewModel @Inject constructor(
    private val moviesPagingSource: MoviesPagingSource,
) : ViewModel() {

  fun getRecentMovies() : Flow<PagingData<Movie>> {
    return Pager(
        PagingConfig(pageSize = 20),
    ) {
        moviesPagingSource
    }.flow.cachedIn(viewModelScope)
  }
}