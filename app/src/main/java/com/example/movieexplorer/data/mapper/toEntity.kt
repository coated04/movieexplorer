package com.example.movieexplorer.data.mapper

import com.example.movieexplorer.data.local.FavoriteMovieEntity
import com.example.movieexplorer.domain.model.Movie

fun Movie.toEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    rating = rating
)
fun FavoriteMovieEntity.toMovie(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath ?: "",      // если nullable, заменяем на пустую строку
    overview = overview,
    releaseDate = releaseDate,
    rating = rating
)

