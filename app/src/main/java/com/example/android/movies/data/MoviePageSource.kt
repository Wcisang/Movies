package com.example.android.movies.data

import androidx.paging.PagingSource
import com.example.android.movies.data.remote.MovieService
import com.example.android.movies.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MoviePageSource(
    private val service: MovieService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        return try {
            val response = service.getPopularMovies(position)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (io: IOException) {
            LoadResult.Error(io)
        }catch (http: HttpException) {
            LoadResult.Error(http)
        }
    }
}