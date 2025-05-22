package com.example.movieexplorer.domain.model

import com.example.movieexplorer.data.local.FavoriteMovieEntity

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String?,
    val releaseDate: String?,
    val rating: Double
)
fun FavoriteMovieEntity.toMovie(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath ?: "",      // если nullable, заменяем на пустую строку
    overview = overview,
    releaseDate = releaseDate,
    rating = rating
)
