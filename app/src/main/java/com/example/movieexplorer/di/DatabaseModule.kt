package com.example.movieexplorer.di

import android.content.Context
import androidx.room.Room
import com.example.movieexplorer.data.local.MovieDatabase
import com.example.movieexplorer.data.local.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(db: MovieDatabase): FavoriteMovieDao {
        return db.favoriteMovieDao()
    }
}
