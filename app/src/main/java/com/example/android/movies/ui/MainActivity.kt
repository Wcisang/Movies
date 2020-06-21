package com.example.android.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.movies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private var moviesJob: Job? = null
    private val moviesAdapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
        createRecyclerView()
        searchPopularMovies()
    }

    private fun setupListeners() {
        btMainRetry.setOnClickListener { moviesAdapter.retry() }
    }

    private fun createRecyclerView() {
        rvMovies.run {
            adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { moviesAdapter.retry() },
                footer = MovieLoadStateAdapter { moviesAdapter.retry() }
            )
        }

        moviesAdapter.addLoadStateListener { loadState ->

            rvMovies.isVisible = loadState.refresh is LoadState.NotLoading
            pbMainLoading.isVisible = loadState.refresh is LoadState.Loading
            btMainRetry.isVisible = loadState.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    this,
                    "Error : ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun searchPopularMovies() {
        moviesJob?.cancel()
        moviesJob = lifecycleScope.launch {
            viewModel.getPopularMovies().collectLatest {
                moviesAdapter.submitData(it)
            }
        }
    }

}