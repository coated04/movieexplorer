package com.example.movieexplorer.domain.repository

import com.example.movieexplorer.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {
    suspend fun addToFavorites(movie: Movie)
    suspend fun removeFromFavorites(movie: Movie)
    fun isFavorite(movieId: Int): Flow<Boolean>
    fun getAllFavorites(): Flow<List<Movie>>
}
