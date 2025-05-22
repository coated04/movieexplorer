package com.example.movieexplorer.data.repository

import com.example.movieexplorer.data.local.dao.FavoriteMovieDao
import com.example.movieexplorer.data.mapper.toEntity
import com.example.movieexplorer.data.mapper.toMovie
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.domain.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
) : FavoriteMovieRepository {

    override suspend fun addToFavorites(movie: Movie) {
        dao.addToFavorites(movie.toEntity())
    }

    override suspend fun removeFromFavorites(movie: Movie) {
        dao.removeFromFavorites(movie.toEntity())
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return dao.isFavorite(movieId)
    }

    override fun getAllFavorites(): Flow<List<Movie>> {
        return dao.getAllFavorites().map { it.map { entity -> entity.toMovie() } }
    }
}
