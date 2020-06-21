package com.example.android.movies.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.movies.R
import kotlinx.android.synthetic.main.movie_item_load_state.view.*

class MovieLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<MovieLoadStateAdapter.LoadStatusViewHolder>() {

    override fun onBindViewHolder(holder: LoadStatusViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_load_state, parent, false)
        return LoadStatusViewHolder(view, retry)
    }

    class LoadStatusViewHolder(
        item: View,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(item) {

        fun bind(loadState : LoadState) {
            itemView.btRetry.setOnClickListener { retry.invoke() }
            if (loadState is LoadState.Error) {
                itemView.tvError.text = loadState.error.localizedMessage
            }
            itemView.pbLoading.isVisible = loadState is LoadState.Loading
            itemView.btRetry.isVisible = loadState !is LoadState.Loading
            itemView.tvError.isVisible = loadState !is LoadState.Loading
        }
    }
}