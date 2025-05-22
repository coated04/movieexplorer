package com.example.movieexplorer.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.example.movieexplorer.domain.model.Movie

data class MovieDetailsResponse(
    val id: Int,
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String,       // Make sure this matches exactly

    val runtime: Int?,

    @SerializedName("rating")
    val rating: Double,       // And this

    @SerializedName("vote_count")
    val voteCount: Int,



    @SerializedName("backdrop_path")
    val backdropPath: String?,

    val tagline: String?,
    val status: String
) {
    fun toMovie(): Movie {
        val posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
        return Movie(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterUrl,
            releaseDate = releaseDate,   // This must be exactly the same property declared above
            rating = rating    // Same here
        )
    }
}
