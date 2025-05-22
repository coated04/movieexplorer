package com.example.movieexplorer.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movieexplorer.presentation.viewmodel.MovieViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieViewModel = hiltViewModel()
) {
    // Trigger loading movie details once when movieId changes
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    val movie = viewModel.selectedMovie.collectAsState().value

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1B2A))
            .padding(16.dp),
        color = Color(0xFF0D1B2A)
    ) {
        movie?.let {
            val isFavorite = viewModel.isFavorite(it.id).collectAsState(initial = false).value

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A5F)),
                modifier = Modifier.fillMaxSize(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        it.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    it.posterPath?.let { path ->
                        Image(
                            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500$path"),
                            contentDescription = it.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        it.overview.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFDAEAF6)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.toggleFavorite(it) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF58A6FF),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Loading movie details...",
                    color = Color(0xFF58A6FF),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
