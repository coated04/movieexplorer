package com.example.movieexplorer.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.presentation.viewmodel.MovieViewModel

@Composable
fun FavoritesScreen(
    viewModel: MovieViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val favorites by viewModel.favoriteMovieObjects.collectAsState()

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No favorites yet.")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            items(favorites) { movie ->
                FavoriteMovieItem(movie = movie, onClick = { onMovieClick(movie) })
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${movie.posterPath}")
        Image(
            painter = painter,
            contentDescription = movie.title,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
