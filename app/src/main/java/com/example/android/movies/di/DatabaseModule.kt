package com.example.android.movies.di

import android.content.Context
import com.example.android.movies.db.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ) : MoviesDataBase{
        return MoviesDataBase.getInstance(context)
    }

}