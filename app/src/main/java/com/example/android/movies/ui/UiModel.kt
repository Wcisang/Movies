package com.example.android.movies.ui

import com.example.android.movies.domain.model.Movie

sealed class UiModel {
    data class MovieItem(val movie: Movie) : UiModel(){
        val popularityCount: Int
            get() = (this.movie.popularity / 10).toInt()
    }
    data class SeparatorItem(val description: String) : UiModel()
}