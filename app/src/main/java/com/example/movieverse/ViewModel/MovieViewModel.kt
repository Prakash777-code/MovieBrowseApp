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

class MovieViewModel : ViewModel() {

    private val repository = MovieRepository()

    val searchData = MutableLiveData<List<Search>?>()
    val state = MutableLiveData<UiState>()
    val errorMessage = MutableLiveData<String>()
    val detailData = MutableLiveData<MovieDetailResponse>()

    fun recieveMovieSearched(title: String) {

        if (title.isBlank()) {
            errorMessage.value = AppConstants.EMPTY_INPUT
            state.value = UiState.ERROR
            return
        }

        viewModelScope.launch {
            state.value = UiState.LOADING
            try {
                val searchResult =
                    repository.getMovieSearched(BuildConfig.APP_API_KEY, title)

                if (searchResult.response == "True" &&
                    !searchResult.search.isNullOrEmpty()
                ) {
                    searchData.value = searchResult.search
                    state.value = UiState.SUCCESS
                } else {
                    searchData.value = emptyList()
                    errorMessage.value = searchResult.error ?: AppConstants.MOVIE_NOT_FOUND
                    state.value = UiState.ERROR
                }

            } catch (e: Exception) {
                errorMessage.value = AppConstants.MOVIE_NOT_FOUND
                state.value = UiState.ERROR
            }
        }
    }

    fun recieveMovieDetail(imdbID: String) {

        if (imdbID.isBlank()) {
            errorMessage.value = AppConstants.INVALID_MOVIE_NAME
            state.value = UiState.ERROR
            return
        }

        viewModelScope.launch {
            state.value = UiState.LOADING
            try {
                val detailResult =
                    repository.fetchMovieDetails(BuildConfig.APP_API_KEY, imdbID)

                detailData.value = detailResult
                state.value = UiState.SUCCESS

            } catch (e: Exception) {
                errorMessage.value = AppConstants.DETAIL_NOT_FOUND
                state.value = UiState.ERROR
            }
        }
    }
}