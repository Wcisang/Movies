package com.example.android.movies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.movies.domain.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Movie>)

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clearMovies()
}