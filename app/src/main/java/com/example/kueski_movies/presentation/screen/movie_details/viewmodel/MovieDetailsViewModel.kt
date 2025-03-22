package com.example.kueski_movies.presentation.screen.movie_details.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kueski_movies.data.remote.model.MovieDetailsResponse
import com.example.kueski_movies.data.repositories.MovieRepository
import com.example.kueski_movies.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  private val repository: MovieRepository
) : ViewModel() {
  private val _state = mutableStateOf<UiState<MovieDetailsResponse?>>(UiState.Loading)
  val state: State<UiState<MovieDetailsResponse?>> = _state

  fun getMovie(id: Int) {
    viewModelScope.launch {
      try {
        val result = repository.getMovie(id.toString())
        _state.value = UiState.Success(result)
      } catch (e: Exception) {
        _state.value = UiState.Failure(e)
      }
    }
  }
}