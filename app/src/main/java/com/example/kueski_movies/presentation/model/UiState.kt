package com.example.kueski_movies.presentation.model

sealed class UiState<out T> {
  data object Loading : UiState<Nothing>()
  data class Success<out T>(val data: T): UiState<T>()
  data class Failure(val e: Exception?): UiState<Nothing>()
}