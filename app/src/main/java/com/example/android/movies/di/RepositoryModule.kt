package com.example.android.movies.di

import android.content.Context
import com.example.android.movies.data.MovieRepository
import com.example.android.movies.data.local.MovieDao
import com.example.android.movies.data.remote.MovieService
import com.example.android.movies.db.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMovieRepository(
        movieService: MovieService
    ): MovieRepository {
        return MovieRepository(
            movieService )
    }
}