package com.example.kueski_movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kueski_movies.presentation.screen.explore_movies.ExploreMoviesScreen
import com.example.kueski_movies.presentation.screen.movie_details.MovieDetailsScreen

@Composable
fun NavigationConfig() {
  val navController = rememberNavController()
  NavHost(
    navController = navController,
    startDestination = Route.ExploreMovies.route
  ) {
    composable(route = Route.ExploreMovies.route) {
      ExploreMoviesScreen(
        onGoToMovieDetails = { navController.navigate(Route.MovieDetails.getRoute(it)) }
      )
    }

    composable(
      route = Route.MovieDetails.route,
      arguments = listOf(
        navArgument(Route.MovieDetails.ID) { type = NavType.IntType },
      ),
    ) { backStackEntry ->
      val movieId = Route.MovieDetails.getId(backStackEntry.arguments)
      MovieDetailsScreen(
        movieId = movieId,
        onGoBack = { navController.popBackStack() },
      )
    }
  }
}