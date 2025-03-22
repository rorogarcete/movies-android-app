package com.example.kueski_movies.presentation.screen.explore_movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun ExploreMoviesScreen(
  viewModel: ExploreMoviesViewModel = hiltViewModel(),
  onGoToMovieDetails: (movieId: Int) -> Unit,
) {
  val movies = remember { viewModel.getRecentMovies() }.collectAsLazyPagingItems()

  ExploreMoviesContent(
    movies = movies,
    onGoToMovieDetails = onGoToMovieDetails,
  )
}