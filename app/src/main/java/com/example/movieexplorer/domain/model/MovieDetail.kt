// MovieDetail.kt
package com.example.movieexplorer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import retrofit2.http.GET
import retrofit2.http.Path

// --- Retrofit API interface ---
interface MovieApiService {
    @GET("movie/{movie_id}?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsResponse
}

// --- Data class for movie details response ---
data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val runtime: Int,
    val poster_path: String?,
    val rating: Double
)

// --- ViewModel ---
class MovieDetailViewModel(private val api: MovieApiService) : ViewModel() {

    var movie by mutableStateOf<MovieDetailsResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                movie = api.getMovieDetails(movieId)
            } catch (e: Exception) {
                errorMessage = "Error loading movie details"
            }
            isLoading = false
        }
    }
}

// --- Composable UI ---
@Composable
fun MovieDetailScreen(movieId: Int, viewModel: MovieDetailViewModel) {
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    val movie = viewModel.movie
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
            movie != null -> {
                MovieDetailContent(movie)
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: MovieDetailsResponse) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        movie.poster_path?.let { posterPath ->
            val imageUrl = "https://image.tmdb.org/t/p/w500$posterPath"
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(movie.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Release Date: ${movie.release_date}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Duration: ${movie.runtime} min", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Rating: ${movie.rating}/10", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(movie.overview, style = MaterialTheme.typography.bodyLarge)
    }
}
