package com.example.android.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.movies.data.remote.MovieService
import com.example.android.movies.db.MoviesDataBase
import com.example.android.movies.domain.model.Movie
import com.example.android.movies.domain.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

private const val MOVIE_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val remote: MovieService,
    private val local: MoviesDataBase
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MOVIE_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: throw InvalidObjectException("Remote key and the prevKey should not be null")
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {
            val apiResponse = remote.getPopularMovies(page)

            val repos = apiResponse.results
            repos.sortedBy { it.id }
            val endOfPaginationReached = repos.isEmpty()
            local.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    local.remoteKeysDao().clearRemoteKeys()
                    local.movieDao().clearMovies()
                }
                val prevKey = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                local.remoteKeysDao().insertAll(keys)
                local.movieDao().insertAll(repos)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                local.remoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                local.remoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                local.remoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }

}