package com.example.movieexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.domain.repository.FavoriteMovieRepository
import com.example.movieexplorer.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    val filteredMovies: StateFlow<List<Movie>> = _searchQuery
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            flow {
                val movies = movieRepository.searchMovies(query)
                emit(movies)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    // ✅ Favorites already exposed as Flow<List<Movie>>
    val favoriteMovieObjects: StateFlow<List<Movie>> = favoriteRepository
        .getAllFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> get() = _selectedMovie

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val movie = movieRepository.getMovieDetails(movieId)
            _selectedMovie.value = movie
        }
    }

    // ✅ Use first() instead of collect to avoid re-collecting
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            val isFav = favoriteRepository.isFavorite(movie.id).first()
            if (isFav) {
                favoriteRepository.removeFromFavorites(movie)
            } else {
                favoriteRepository.addToFavorites(movie)
            }
        }
    }

    // ✅ Used as is to expose favorite status reactively
    fun isFavorite(movieId: Int): Flow<Boolean> = favoriteRepository.isFavorite(movieId)
}
