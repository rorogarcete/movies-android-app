package com.example.kueski_movies.presentation.screen.movie_details.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kueski_movies.R
import com.example.kueski_movies.domain.models.MovieDetail
import com.example.kueski_movies.domain.models.ProductionCompany
import com.example.kueski_movies.domain.models.ProductionCountry
import com.example.kueski_movies.presentation.model.UiState
import com.example.kueski_movies.presentation.shared.component.AsyncImage
import com.example.kueski_movies.ui.theme.KueskiMoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(
  uiState: UiState<MovieDetail?>,
  onGoBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Movie Details") },
        navigationIcon = {
          IconButton(onClick = onGoBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null,
            )
          }
        },
      )
    },
  ) { paddingValues ->
    when (uiState) {
      is UiState.Success -> {
        uiState.data?.let {
          MovieDetails(
            modifier = Modifier.padding(paddingValues),
            movieDetail = it
          )
        } ?: NotFoundMovie(modifier = Modifier.padding(paddingValues))
      }
      is UiState.Loading -> {
        CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
      }
      is UiState.Failure -> {
        Text("Something went wrong", modifier = Modifier.padding(paddingValues))
      }
    }
  }
}

@Composable
fun MovieDetails(
  movieDetail: MovieDetail,
  modifier: Modifier = Modifier,
) {
  var producers by remember { mutableStateOf("") }
  if (producers.isEmpty() && movieDetail.productionCompanies.isNotEmpty()) {
    val productionBuffer = StringBuffer()
    productionBuffer.apply {
      appendLine("ProducedBy: ")
      for (company in movieDetail.productionCompanies) {
        appendLine("- ${company.name}")
      }
    }
    producers = productionBuffer.toString()
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp)
      .padding(bottom = 24.dp)
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
    AsyncImage(
      imagePath = movieDetail.posterPath ?: "",
      modifier = Modifier.fillMaxWidth(),
    )
    Row(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      IconButton(onClick = {}) {
        Icon(
          imageVector = Icons.Filled.FavoriteBorder,
          contentDescription = null,
        )
      }
      Text(
        text = movieDetail.title,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
      )
    }
    Row(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Image(
        painter = painterResource(R.drawable.ic_star),
        contentDescription = null,
      )
      Text(
        text = movieDetail.voteAverage.toString(),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
      )
    }
    Text(
      text = movieDetail.overview,
      style = MaterialTheme.typography.bodyLarge,
    )
    Text(
      text = "Release date: ${movieDetail.releaseDate}",
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
    )
    if (producers.isNotEmpty()) {
      Text(producers, modifier = Modifier.align(Alignment.Start))
    }
  }
}

@Composable
fun NotFoundMovie(
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Text("Movie was not found")
  }
}

@Preview
@Composable
fun MovieDetailsContentPreview() {
  val movieDetails = MovieDetail(
    id = -1,
    title = "My movie Title",
    overview = "My movie overview with a text that should be large enough in order to take multiple lines and help us to look if it looks good",
    voteAverage = 9.4,
    releaseDate = "10-20-2024",
    posterPath = "somePath",
    productionCompanies = listOf(
      ProductionCompany(
        id = -1,
        logoPath = "",
        name = "My Production",
        originCountry = "My country",
      ),
    ),
    productionCountries = listOf(
      ProductionCountry(
        iso31661 = "iso",
        name = "My Country",
      ),
    ),
  )
  KueskiMoviesTheme {
    MovieDetailsContent(
      uiState = UiState.Success(movieDetails),
      onGoBack = {},
    )
  }
}