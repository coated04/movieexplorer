package com.example.movieexplorer.domain.usecase

import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> = repository.getPopularMovies()
}
