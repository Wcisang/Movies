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
    private val movieService: MovieService
) {

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { MoviePageSource(movieService) }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}