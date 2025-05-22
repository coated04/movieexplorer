package com.example.movieexplorer.data.local.dao

import androidx.room.*
import com.example.movieexplorer.data.local.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(movie: FavoriteMovieEntity)

    @Delete
    suspend fun removeFromFavorites(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :movieId)")
    fun isFavorite(movieId: Int): Flow<Boolean>
}
