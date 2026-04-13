package com.example.movieverse.Ui

import com.example.movieverse.Models.Search

sealed class SearchUiState {

    data class SearchSuccess(val data: List<Search>): SearchUiState()
    object Loading: SearchUiState()
    data class Error(val message: String): SearchUiState()
}