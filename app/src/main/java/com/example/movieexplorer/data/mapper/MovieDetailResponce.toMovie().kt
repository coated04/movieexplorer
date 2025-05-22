package com.example.movieexplorer.data.mapper

import com.example.movieexplorer.data.remote.MovieDto
import com.example.movieexplorer.data.remote.dto.MovieDetailsResponse
import com.example.movieexplorer.domain.model.Movie

fun MovieDetailsResponse.toMovie(): Movie {
    val posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
    return Movie(
        id = id,
        title = title,
        posterPath = posterUrl,
        overview = overview,
        releaseDate = releaseDate,
        rating = rating
    )
}

fun MovieDto.toMovie(): Movie {
    val posterPath = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        rating = rating,
        releaseDate = TODO()     // Аналогично
    )
}

