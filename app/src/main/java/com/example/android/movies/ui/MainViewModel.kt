package com.example.android.movies.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.android.movies.data.MovieRepository
import com.example.android.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel @ViewModelInject constructor(
    private val repository: MovieRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getPopularMovies() : Flow<PagingData<UiModel>> {
        return repository.getPopularMovies()
            .map { pagingData -> pagingData.map { UiModel.MovieItem(it) } }
            .map { 
                it.insertSeparators<UiModel.MovieItem, UiModel> { before, after ->
                    if (after == null)
                        return@insertSeparators null

                    if (before == null)
                        return@insertSeparators UiModel.SeparatorItem("${after.popularityCount}0+ stars")

                    if (before.popularityCount > after.popularityCount) {
                        if (after.popularityCount >= 1) {
                            UiModel.SeparatorItem("${after.popularityCount}0+ popularidade")
                        } else {
                            UiModel.SeparatorItem("< 10+ popularidade")
                        }
                    } else {
                        null
                    }
                }
            }.cachedIn(viewModelScope)
    }
}