package com.example.movieexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieexplorer.data.local.dao.FavoriteMovieDao

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
