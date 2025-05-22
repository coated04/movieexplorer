package com.example.movieexplorer.data.remote

import com.example.movieexplorer.domain.model.Movie

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val rating: Double,
    val release_date: String
) {
    fun toMovie(): Movie {
        val posterUrl = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
        return Movie(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterUrl,
            releaseDate = release_date,
            rating = rating
        )
    }
}
