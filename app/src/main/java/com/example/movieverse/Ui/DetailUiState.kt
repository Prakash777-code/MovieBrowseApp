package com.example.movieverse.Ui

import com.example.movieverse.Models.MovieDetailResponse

sealed class DetailUiState {

    object Loading : DetailUiState()
    data class DetailSuccess(val data: MovieDetailResponse) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}