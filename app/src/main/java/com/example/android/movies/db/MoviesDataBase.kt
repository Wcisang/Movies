package com.example.android.movies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.movies.data.local.MovieDao
import com.example.android.movies.data.local.RemoteKeysDao
import com.example.android.movies.domain.model.Movie
import com.example.android.movies.domain.model.RemoteKeys

@Database(
    entities = [Movie::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDataBase? = null

        fun getInstance(context: Context): MoviesDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesDataBase::class.java, "Movies.db"
            ).build()
    }
}