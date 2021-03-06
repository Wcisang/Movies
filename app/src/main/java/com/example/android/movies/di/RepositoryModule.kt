package com.example.android.movies.di

import com.example.android.movies.data.MovieRepository
import com.example.android.movies.data.remote.MovieService
import com.example.android.movies.db.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMovieRepository(
        movieService: MovieService,
        moviesDataBase: MoviesDataBase
    ): MovieRepository {
        return MovieRepository(
            movieService,
            moviesDataBase
        )
    }
}