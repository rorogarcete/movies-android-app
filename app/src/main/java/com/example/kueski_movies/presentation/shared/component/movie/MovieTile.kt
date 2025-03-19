package com.example.kueski_movies.presentation.shared.component.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.kueski_movies.presentation.shared.component.AsyncImage
import com.example.kueski_movies.ui.theme.KueskiMoviesTheme

@Composable
fun MovieTile(
  imagePath: String,
  title: String,
  releaseDate: String,
  onClick: () -> Unit,
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    onClick = onClick,
  ) {
    Row {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(12.dp).fillMaxWidth()
      ) {
        AsyncImage(
          imagePath = imagePath,
          modifier = Modifier.fillMaxWidth().height(150.dp),
        )
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium,
          textAlign = TextAlign.Center,
        )
        Row(
          verticalAlignment = Alignment.CenterVertically,
        ) {
          IconButton(onClick = {}) {
            Icon(
              imageVector = Icons.Filled.FavoriteBorder,
              contentDescription = null,
            )
          }
          Text(
            text = releaseDate,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
          )
        }
      }
    }
  }
}

@Composable
@PreviewLightDark
fun MovieTilePreview() {
  KueskiMoviesTheme {
    MovieTile(
      imagePath = "something",
      title = "My Movie Title",
      releaseDate = "12-12-21",
      onClick = {}
    )
  }
}