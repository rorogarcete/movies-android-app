package com.example.kueski_movies.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kueski_movies.presentation.navigation.NavigationConfig
import com.example.kueski_movies.ui.theme.KueskiMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
        KueskiMoviesTheme {
            NavigationConfig()
        }
    }
  }
}