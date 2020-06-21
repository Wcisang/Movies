package com.example.android.movies.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.android.movies.R
import com.example.android.movies.domain.model.Movie
import com.example.android.movies.network.NetworkConstants.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlinx.android.synthetic.main.separator_item.view.*

class MoviesAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            when (item) {
                is UiModel.MovieItem -> (holder as ViewHolder).bind(item.movie)
                is UiModel.SeparatorItem -> (holder as SeparatorHolder).bind(item.description)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return if (viewType == R.layout.movie_item) {
            ViewHolder(view)
        } else {
            SeparatorHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.MovieItem -> R.layout.movie_item
            is UiModel.SeparatorItem -> R.layout.separator_item
            null -> throw Exception("")
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun bind(movie: Movie) {
            itemView.tvMovieName.text = movie.title
            itemView.ivMovieImage.load(IMAGE_BASE_URL + movie.poster_path) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
        }
    }

    class SeparatorHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(text: String) {
            itemView.tvSeparatorText.text = text
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.MovieItem && newItem is UiModel.MovieItem &&
                        oldItem.movie.id == newItem.movie.id) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }
    }
}