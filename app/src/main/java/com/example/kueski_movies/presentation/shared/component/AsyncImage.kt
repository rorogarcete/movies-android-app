package com.example.kueski_movies.presentation.shared.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.kueski_movies.BuildConfig

@Composable
fun AsyncImage(imagePath: String, modifier: Modifier = Modifier) {
  SubcomposeAsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
      .data(BuildConfig.IMG_URL + imagePath)
      .build(),
    contentScale = ContentScale.Crop,
    contentDescription = null,
    modifier = modifier,
  ) {
    when (painter.state) {
      is AsyncImagePainter.State.Error -> Text("error")
      is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
      else -> SubcomposeAsyncImageContent()
    }
  }
}