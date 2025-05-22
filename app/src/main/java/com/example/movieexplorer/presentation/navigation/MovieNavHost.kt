package com.example.movieexplorer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.movieexplorer.presentation.ui.MovieListScreen
import com.example.movieexplorer.presentation.ui.MovieDetailsScreen
import com.example.movieexplorer.presentation.ui.FavoritesScreen
import com.example.movieexplorer.presentation.viewmodel.MovieViewModel

object Routes {
    const val MOVIE_LIST = "movie_list"
    const val MOVIE_DETAILS = "movie_details/{movieId}"
    const val FAVORITES = "favorites"

    fun movieDetails(movieId: Int) = "movie_details/$movieId"
}

@Composable
fun MovieNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "movie_graph"  // Root NavHost starts at nested graph
    ) {
        navigation(
            startDestination = Routes.MOVIE_LIST,
            route = "movie_graph"
        ) {
            composable(Routes.MOVIE_LIST) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("movie_graph")
                }
                val viewModel: MovieViewModel = hiltViewModel(parentEntry)
                MovieListScreen(navController = navController, viewModel = viewModel)
            }

            composable(Routes.MOVIE_DETAILS) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("movie_graph")
                }
                val viewModel: MovieViewModel = hiltViewModel(parentEntry)

                if (movieId != null) {
                    MovieDetailsScreen(movieId = movieId, viewModel = viewModel)
                }
            }

            composable(Routes.FAVORITES) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("movie_graph")
                }
                val viewModel: MovieViewModel = hiltViewModel(parentEntry)

                FavoritesScreen(
                    viewModel = viewModel,
                    onMovieClick = { movie ->
                        navController.navigate(Routes.movieDetails(movie.id))
                    }
                )
            }
        }
    }
}
