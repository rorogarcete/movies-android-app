package com.example.kueski_movies.presentation.screen.favorite_movies.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kueski_movies.data.remote.model.MovieResponse
import com.example.kueski_movies.presentation.model.UiState
import com.example.kueski_movies.presentation.shared.component.movie.MovieTile
import com.example.kueski_movies.ui.theme.KueskiMoviesTheme

@Composable
fun FavoriteMoviesContent(
  uiState: UiState<List<MovieResponse>>,
  onGoToMovieDetails: (movieId: Int) -> Unit,
) {
  Scaffold { safeArea ->
    when (uiState) {
      is UiState.Success -> {
        FavoriteMoviesList(
          modifier = Modifier.padding(safeArea),
          movies = uiState.data,
          onGoToMovieDetails = onGoToMovieDetails,
        )
      }
      is UiState.Loading -> {
        CircularProgressIndicator()
      }
      is UiState.Failure -> {
        Text("Something went wrong")
      }
    }
  }
}

@Composable
fun FavoriteMoviesList(
  modifier: Modifier = Modifier,
  movies: List<MovieResponse>,
  onGoToMovieDetails: (movieId: Int) -> Unit,
) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 12.dp)
  ) {
    items(movies) {
      MovieTile(
        imagePath = it.posterPath ?: "",
        title = it.title,
        releaseDate = it.releaseDate,
        onClick = { onGoToMovieDetails(it.id) }
      )
    }
  }
}

@Preview
@Composable
fun PreviewFavoriteMoviesList() {
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
    FavoriteMoviesContent(
      uiState = UiState.Success(movies),
      onGoToMovieDetails = {},
    )
  }
}