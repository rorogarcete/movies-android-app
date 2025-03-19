package com.example.kueski_movies.presentation.screen.explore_movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kueski_movies.presentation.screen.explore_movies.component.ExploreMoviesContent
import com.example.kueski_movies.presentation.screen.explore_movies.viewmodel.ExploreMoviesViewModel

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