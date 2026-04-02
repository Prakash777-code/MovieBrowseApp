package com.example.movieverse.Ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieverse.Adapter.MovieAdapter
import com.example.movieverse.R
import com.example.movieverse.Utils.AppConstants
import com.example.movieverse.Utils.UiState
import com.example.movieverse.ViewModel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var movieInput: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var vm: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        movieInput = findViewById(R.id.movieInput)
        btnSearch = findViewById(R.id.btnSearch)
        recyclerView = findViewById(R.id.recyclerview)
        progressBar = findViewById(R.id.progressBar)

        vm = ViewModelProvider(this)[MovieViewModel::class.java]

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(emptyList()) { movie ->
            val imdbID = movie.imdbID
            if (!imdbID.isNullOrBlank()) {
                startActivity(
                    Intent(this, MovieDetailActivity::class.java)
                        .putExtra(AppConstants.IMDBID, imdbID)
                )
            } else {
                Toast.makeText(this, AppConstants.DETAIL_NOT_FOUND, Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter


        vm.searchData.observe(this) { movieList ->
            adapter.updateList(movieList)
        }

        vm.state.observe(this) { state ->

            when(state){
                UiState.LOADING -> progressBar.visibility = View.VISIBLE
                UiState.SUCCESS -> progressBar.visibility = View.GONE
                UiState.ERROR -> progressBar.visibility = View.GONE
            }
        }

        vm.errorMessage.observe(this) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }

        btnSearch.setOnClickListener {
            val query = movieInput.text.toString().trim()
            if (query.isNotEmpty()) {
                vm.recieveMovieSearched(query)
            } else {
                Toast.makeText(this, AppConstants.EMPTY_INPUT, Toast.LENGTH_SHORT).show()
            }
        }

        if(savedInstanceState == null){
            vm.recieveMovieSearched(AppConstants.INITIAL_SEARCH)
            movieInput.setText(AppConstants.INITIAL_SEARCH)
        }
    }
}