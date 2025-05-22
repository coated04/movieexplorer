package com.example.movieexplorer.domain.repository

import com.example.movieexplorer.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieDetails(movieId: Int): Movie

}
