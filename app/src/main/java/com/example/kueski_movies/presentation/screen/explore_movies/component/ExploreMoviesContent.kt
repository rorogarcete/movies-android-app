package com.example.kueski_movies.presentation.screen.explore_movies.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kueski_movies.data.movies.remote.model.MovieResponse
import com.example.kueski_movies.presentation.model.UiState
import com.example.kueski_movies.presentation.shared.component.movie.MovieTile
import com.example.kueski_movies.ui.theme.KueskiMoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreMoviesContent(
  moviesState: UiState<List<MovieResponse>>,
  onGoToMovieDetails: (movieId: Int) -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Explore") }
      )
    }
  ) { paddingValues ->
    when (moviesState) {
      is UiState.Success -> {
        Column(
          modifier = Modifier.padding(paddingValues).padding(horizontal = 12.dp)
        ) {
          LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
          ) {
            items(moviesState.data) {
              MovieTile(
                imagePath = it.posterPath ?: "",
                title = it.title,
                overview = it.releaseDate,
                onClick = { onGoToMovieDetails(it.id) }
              )
            }
          }
        }
      }
      is UiState.Loading -> {
        CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
      }
      is UiState.Failure -> {
        Text(
          text = "Something went wrong",
          modifier = Modifier.padding(paddingValues),
        )
      }
    }
  }
}

@Preview
@Composable
fun ExploreMoviesContentSuccessPreview() {
  val movies = List(
    size = 10,
    init = {
      MovieResponse(
        adult = false,
        backdropPath = null,
        genreIds = listOf(),
        id = 1,
        originalTitle = "Some title$it",
        originalLanguage = "en-US",
        overview = "$it Some Overview that should how this fits in tiles with a very large text somehow",
        popularity = 5.0,
        posterPath = null,
        releaseDate = "$it-10-22",
        title = "Some title$it",
        video = false,
        voteAverage = 5.0,
        voteCount = 1000,
      )
    },
  )
  KueskiMoviesTheme {
    ExploreMoviesContent(
      moviesState = UiState.Success(movies),
      onGoToMovieDetails = {},
    )
  }
}

@Preview
@Composable
fun ExploreMoviesContentLoadingPreview() {
  KueskiMoviesTheme {
    ExploreMoviesContent(
      moviesState = UiState.Loading,
      onGoToMovieDetails = {}
    )
  }
}

@Preview
@Composable
fun ExploreMoviesContentFailurePreview() {
  KueskiMoviesTheme {
    ExploreMoviesContent(
      moviesState = UiState.Failure(Exception()),
      onGoToMovieDetails = {},
    )
  }
}