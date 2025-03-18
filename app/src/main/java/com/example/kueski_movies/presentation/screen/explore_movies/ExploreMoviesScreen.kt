package com.example.kueski_movies.presentation.screen.explore_movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kueski_movies.presentation.screen.explore_movies.component.ExploreMoviesContent
import com.example.kueski_movies.presentation.screen.explore_movies.viewmodel.ExploreMoviesViewModel

@Composable
fun ExploreMoviesScreen(
  viewModel: ExploreMoviesViewModel = hiltViewModel(),
  onGoToMovieDetails: (movieId: Int) -> Unit,
) {
  LaunchedEffect(Unit) {
    viewModel.getRecentMovies()
  }

  ExploreMoviesContent(
    moviesState = viewModel.state.value,
    onGoToMovieDetails = onGoToMovieDetails,
  )
}