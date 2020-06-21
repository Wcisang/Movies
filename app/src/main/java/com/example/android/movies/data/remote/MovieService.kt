package com.example.android.movies.data.remote

import com.example.android.movies.domain.model.PopularMovieResponse
import com.example.android.movies.network.NetworkConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(NetworkConstants.POPULAR_ENDPOINT)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): PopularMovieResponse
}