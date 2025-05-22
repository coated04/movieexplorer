package com.example.movieexplorer.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.movieexplorer.presentation.navigation.Routes
import com.example.movieexplorer.presentation.viewmodel.MovieViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navController: NavHostController,
    viewModel: MovieViewModel
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val movieList by viewModel.filteredMovies.collectAsState()

    Scaffold(
        containerColor = Color(0xFF0D1B2A), // Deep bluish background
        topBar = {
            TopAppBar(
                title = {
                    Text("Movie Explorer", fontSize = 20.sp, color = Color(0xFFDAEAF6))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B263B)
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.FAVORITES) }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites",
                            tint = Color(0xFF58A6FF)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF0D1B2A))
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Search", color = Color(0xFFDAEAF6)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF58A6FF),
                    unfocusedBorderColor = Color(0xFF3B5360),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF58A6FF)
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movieList) { movie ->
                    var visible by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay(100)
                        visible = true
                    }

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        MovieCard(movie = movie) {
                            navController.navigate(Routes.movieDetails(movie.id))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: com.example.movieexplorer.domain.model.Movie, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E3A5F) // blueish card
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.posterPath),
                contentDescription = movie.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
