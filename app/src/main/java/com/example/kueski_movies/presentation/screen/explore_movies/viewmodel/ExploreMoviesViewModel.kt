package com.example.kueski_movies.presentation.screen.explore_movies.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kueski_movies.data.movies.remote.model.MovieResponse
import com.example.kueski_movies.data.movies.repository.MoviesRepository
import com.example.kueski_movies.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreMoviesViewModel @Inject constructor(
  private val repository: MoviesRepository,
) : ViewModel() {
  private val _state = mutableStateOf<UiState<List<MovieResponse>>>(UiState.Loading)
  val state: State<UiState<List<MovieResponse>>> = _state

  fun getRecentMovies() {
    viewModelScope.launch {
      try {
        val result = repository.getRecentMovies()
        _state.value = UiState.Success(result.results)
      } catch (e: Exception) {
        _state.value = UiState.Failure(e)
      }
    }
  }
}