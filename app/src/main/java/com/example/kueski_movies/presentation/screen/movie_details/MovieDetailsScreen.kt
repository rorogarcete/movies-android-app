package com.example.kueski_movies.presentation.screen.movie_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kueski_movies.presentation.screen.movie_details.component.MovieDetailsContent
import com.example.kueski_movies.presentation.screen.movie_details.viewmodel.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen(
  viewModel: MovieDetailsViewModel = hiltViewModel(),
  movieId: Int,
  onGoBack: () -> Unit,
) {
  LaunchedEffect(Unit) {
    viewModel.getMovie(movieId)
  }

  MovieDetailsContent(viewModel.state.value, onGoBack)
}