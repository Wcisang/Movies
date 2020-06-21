package com.example.android.movies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.movies.data.local.MovieDao
import com.example.android.movies.data.remote.MovieService
import com.example.android.movies.db.MoviesDataBase
import com.example.android.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieService: MovieService,
    private val moviesDataBase: MoviesDataBase
) {

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { moviesDataBase.movieDao().getMovies() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MovieRemoteMediator(movieService, moviesDataBase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}