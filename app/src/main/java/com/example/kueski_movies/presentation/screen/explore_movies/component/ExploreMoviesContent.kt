package com.example.kueski_movies.presentation.screen.explore_movies.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.kueski_movies.data.remote.model.MovieResponse
import com.example.kueski_movies.presentation.shared.component.movie.MovieTile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreMoviesContent(
  movies: LazyPagingItems<MovieResponse>,
  onGoToMovieDetails: (movieId: Int) -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Explore") }
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier.padding(paddingValues).padding(horizontal = 12.dp)
    ) {
      LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = when (movies.itemCount) {
          0 -> remember(movies) { LazyGridState(0, 0) }
          else -> rememberLazyGridState()
        },
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(movies.itemCount) { index ->
          movies[index]?.let { movie ->
            MovieTile(
              imagePath = movie.posterPath ?: "",
              title = movie.title,
              releaseDate = movie.releaseDate,
              onClick = { onGoToMovieDetails(movie.id) }
            )
          }
        }
      }

      when (movies.loadState.append) {
        is LoadState.NotLoading -> {

        }
        is LoadState.Loading -> {
          CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
        }
        is LoadState.Error -> {
          Text(
            text = "Something went wrong",
            modifier = Modifier.padding(paddingValues),
          )
        }
      }
    }
  }
}