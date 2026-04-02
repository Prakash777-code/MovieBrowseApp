package com.example.movieverse.Ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movieverse.Models.MovieDetailResponse
import com.example.movieverse.R
import com.example.movieverse.Utils.AppConstants
import com.example.movieverse.Utils.UiState
import com.example.movieverse.ViewModel.MovieViewModel
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var imgPoster: ImageView
    private lateinit var detailScreenProgressBar: ProgressBar
    private lateinit var tvTitle: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvYear: TextView
    private lateinit var tvRuntime: TextView
    private lateinit var tvReleased: TextView
    private lateinit var tvImdbRating: TextView
    private lateinit var tvImdbVotes: TextView
    private lateinit var tvPlot: TextView
    private lateinit var tvDirector: TextView
    private lateinit var tvWriter: TextView
    private lateinit var tvActors: TextView
    private lateinit var tvLanguage: TextView
    private lateinit var tvCountry: TextView
    private lateinit var tvAwards: TextView
    private lateinit var tvBoxOffice: TextView
    private lateinit var tvProduction: TextView

    private lateinit var vm: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_screen)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        bindViews()

        vm = ViewModelProvider(this)[MovieViewModel::class.java]

        val imdbID = intent.getStringExtra(AppConstants.IMDBID)
        if (imdbID.isNullOrBlank()) {
            Toast.makeText(this, AppConstants.DETAIL_NOT_FOUND, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        observeViewModel()
        vm.recieveMovieDetail(imdbID)
    }

    private fun bindViews() {
        imgPoster = findViewById(R.id.imgPoster)
        detailScreenProgressBar = findViewById(R.id.detailScreenProgressBar)
        tvTitle = findViewById(R.id.tvTitle)
        tvGenre = findViewById(R.id.tvGenre)
        tvYear = findViewById(R.id.tvYear)
        tvRuntime = findViewById(R.id.tvRuntime)
        tvReleased = findViewById(R.id.tvReleased)
        tvImdbRating = findViewById(R.id.tvImdbRating)
        tvImdbVotes = findViewById(R.id.tvImdbVotes)
        tvPlot = findViewById(R.id.tvPlot)
        tvDirector = findViewById(R.id.tvDirector)
        tvWriter = findViewById(R.id.tvWriter)
        tvActors = findViewById(R.id.tvActors)
        tvLanguage = findViewById(R.id.tvLanguage)
        tvCountry = findViewById(R.id.tvCountry)
        tvAwards = findViewById(R.id.tvAwards)
        tvBoxOffice = findViewById(R.id.tvBoxOffice)
        tvProduction = findViewById(R.id.tvProduction)
    }

    private fun observeViewModel() {

        vm.state.observe(this) { state ->
            when (state) {
                UiState.LOADING -> {
                    detailScreenProgressBar.visibility = View.VISIBLE
                    imgPoster.visibility = View.INVISIBLE
                }

                UiState.SUCCESS -> {
                    detailScreenProgressBar.visibility = View.GONE
                    imgPoster.visibility = View.VISIBLE
                }

                UiState.ERROR -> {
                    detailScreenProgressBar.visibility = View.GONE
                    imgPoster.visibility = View.VISIBLE
                    Toast.makeText(this, AppConstants.MOVIE_NOT_FOUND, Toast.LENGTH_SHORT).show()
                }
            }
        }

        vm.errorMessage.observe(this){msg ->
            if(!msg.isNullOrBlank()){
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
            }
        }

        vm.detailData.observe(this) { movie ->
            movie?.let { bindData(it) }
        }
    }

    private fun bindData(movie: MovieDetailResponse) {

        tvTitle.text = movie.Title ?: "N/A"
        tvGenre.text = movie.Genre ?: "N/A"
        tvYear.text = movie.Year ?: "N/A"
        tvRuntime.text = movie.RunTime ?: "N/A"
        tvReleased.text = movie.Released ?: "N/A"

        tvImdbRating.text = movie.imdbRating?.let {
            "IMDb: $it"
        } ?: "IMDb: N/A"

        tvImdbVotes.text = movie.imdbVotes?.let { "Votes: $it"
        } ?: "Votes: N/A"

        tvPlot.text = movie.Plot ?: "N/A"
        tvDirector.text = movie.Director?.let { "Director: $it" } ?: "Director: N/A"
        tvWriter.text = movie.Writer?.let { "Writer: $it" } ?: "Writer: N/A"
        tvActors.text = movie.Actors?.let { "Actors: $it" } ?: "Actors: N/A"
        tvLanguage.text = movie.Language ?: "N/A"
        tvCountry.text = movie.Country ?: "N/A"
        tvAwards.text = movie.Awards ?: "N/A"
        tvBoxOffice.text = movie.BoxOffice ?: "N/A"
        tvProduction.text = movie.Production ?: "N/A"


        if(!movie.Poster.isNullOrEmpty()) {
            Picasso.get()
                .load(movie.Poster)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .resize(1080, 1600)
                .centerInside()
                .into(imgPoster)

        }
        else{
            Toast.makeText(this, AppConstants.POSTER_NOT_AVAILABLE, Toast.LENGTH_SHORT).show()
        }
    }
}