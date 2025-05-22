package com.example.movieexplorer.data.repository

import com.example.movieexplorer.data.remote.MovieApiService
import com.example.movieexplorer.data.remote.dto.MovieDetailsResponse
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    private val apiKey = "8c23f6d1a3052a56cc29c29458c8f124"

    override suspend fun getPopularMovies(): List<Movie> {
        return apiService.getPopularMovies(apiKey).results.map { it.toMovie() }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        if (query.isBlank()) {
            return getPopularMovies()
        }
        return apiService.searchMovies(apiKey, query).results.map { it.toMovie() }
    }


    override suspend fun getMovieDetails(movieId: Int): Movie {
        val response: MovieDetailsResponse = apiService.getMovieDetails(movieId, apiKey)
        return response.toMovie()
    }
}
