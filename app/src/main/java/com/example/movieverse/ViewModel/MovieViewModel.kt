package com.example.movieverse.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieverse.Models.MovieDetailResponse
import com.example.movieverse.Repository.MovieRepository
import com.example.movieverse.Models.Search
import com.example.movieverse.Utils.AppConstants
import com.example.movieverse.Utils.UiState
import kotlinx.coroutines.launch
import com.example.movieverse.BuildConfig

class MovieViewModel() : ViewModel() {

    private val repository = MovieRepository()

    private val _searchData = MutableLiveData<List<Search>?>()
    val searchData get() = _searchData

    private val _state = MutableLiveData<UiState>()
    val state get() = _state

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage get() = _errorMessage

    private val _detailData = MutableLiveData<MovieDetailResponse>()
    val detailData get() = _detailData

    fun recieveMovieSearched(title: String) {

        if (title.isBlank()) {
            errorMessage.value = AppConstants.EMPTY_INPUT
            state.value = UiState.ERROR
            return
        }

        viewModelScope.launch {
            _state.value = UiState.LOADING
            try {
                val searchResult =
                    repository.getMovieSearched( title)

                if (searchResult.response == "True" &&
                    !searchResult.search.isNullOrEmpty()
                ) {
                    _searchData.value = searchResult.search
                    _state.value = UiState.SUCCESS
                } else {
                    _searchData.value = emptyList()
                    _errorMessage.value = searchResult.error ?: AppConstants.MOVIE_NOT_FOUND
                    _state.value = UiState.ERROR
                }

            } catch (e: Exception) {
                _errorMessage.value = AppConstants.MOVIE_NOT_FOUND
                _state.value = UiState.ERROR
            }
        }
    }

    fun recieveMovieDetail(imdbID: String) {

        if (imdbID.isBlank()) {
            _errorMessage.value = AppConstants.INVALID_MOVIE_NAME
            _state.value = UiState.ERROR
            return
        }

        viewModelScope.launch {
            _state.value = UiState.LOADING
            try {
                val detailResult =
                    repository.fetchMovieDetails(imdbID)

                _detailData.value = detailResult
                _state.value = UiState.SUCCESS

            } catch (e: Exception) {
                _errorMessage.value = AppConstants.DETAIL_NOT_FOUND
                _state.value = UiState.ERROR
            }
        }
    }
}