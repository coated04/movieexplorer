package com.example.movieexplorer.di
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.movieexplorer.domain.repository.FavoriteMovieRepository
import com.example.movieexplorer.data.repository.FavoriteMovieRepositoryImpl
import com.example.movieexplorer.domain.repository.MovieRepository
import com.example.movieexplorer.data.repository.MovieRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteMovieRepository(
        impl: FavoriteMovieRepositoryImpl
    ): FavoriteMovieRepository
}
