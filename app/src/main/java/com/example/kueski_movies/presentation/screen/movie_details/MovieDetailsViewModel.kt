package com.example.kueski_movies.presentation.screen.movie_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kueski_movies.data.repositories.MovieRepository
import com.example.kueski_movies.domain.mappers.MovieDetailMapper
import com.example.kueski_movies.domain.models.MovieDetail
import com.example.kueski_movies.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val movieDetailMapper: MovieDetailMapper,
) : ViewModel() {
  private val _state = mutableStateOf<UiState<MovieDetail?>>(UiState.Loading)
  val state: State<UiState<MovieDetail?>> = _state

  fun getMovie(id: Int) {
    viewModelScope.launch {
      try {
        val result = repository.getMovie(id.toString())

        val movieDetail = movieDetailMapper.map(result)

        _state.value = UiState.Success(movieDetail)
      } catch (e: Exception) {
        _state.value = UiState.Failure(e)
      }
    }
  }
}