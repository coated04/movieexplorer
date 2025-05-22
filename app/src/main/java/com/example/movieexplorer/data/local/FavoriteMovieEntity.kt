package com.example.movieexplorer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,       // nullable, совпадает с Movie
    val overview: String?,         // nullable, чтобы не было конфликтов
    val releaseDate: String?,      // nullable
    val rating: Double
)
