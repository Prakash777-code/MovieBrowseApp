package com.example.movieverse.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieverse.Repository.MovieRepository
import com.example.movieverse.Utils.AppConstants
import kotlinx.coroutines.launch
import com.example.movieverse.Ui.DetailUiState
import com.example.movieverse.Ui.SearchUiState
import okio.IOException

class MovieViewModel() : ViewModel() {

    private val repository = MovieRepository()
    private val _state = MutableLiveData<SearchUiState>()
    val state: LiveData<SearchUiState> = _state

    private val _detailState = MutableLiveData<DetailUiState>()
    val detailUiState : LiveData<DetailUiState> = _detailState

    private var currentId: String? = null

    fun recieveMovieSearched(title: String) {

        if (title.isBlank()) {
            _state.value = SearchUiState.Error(AppConstants.EMPTY_INPUT)
            return
        }

        viewModelScope.launch {

            _state.value = SearchUiState.Loading

            try {
                val searchResult = repository.getMovieSearched(title)

                if (searchResult.response == "True" && !searchResult.search.isNullOrEmpty()) {
                    _state.value = SearchUiState.SearchSuccess(searchResult.search)
                } else {
                    _state.value = SearchUiState.Error(searchResult.error?: AppConstants.UNEXPECTED_ERROR)
                }

            } catch (e: IOException){
                _state.value = SearchUiState.Error(AppConstants.NETWORK_ISSUE)
            }
            catch (e: Exception) {

                _state.value = SearchUiState.Error(AppConstants.UNEXPECTED_ERROR)
            }
        }
    }

    fun recieveMovieDetail(imdbID: String) {

        if (imdbID.isBlank()) {
            _detailState.value = DetailUiState.Error(AppConstants.EMPTY_INPUT)
            return
        }

        if (imdbID == currentId) return
        currentId = imdbID

        viewModelScope.launch {

            _detailState.value = DetailUiState.Loading

            try {

                val detailResult = repository.fetchMovieDetails(imdbID)
                _detailState.value = DetailUiState.DetailSuccess(detailResult)

            }catch (e: IOException){
                _detailState.value = DetailUiState.Error(AppConstants.NETWORK_ISSUE)
            }

            catch (e: Exception) {
                _detailState.value = DetailUiState.Error(AppConstants.UNEXPECTED_ERROR)
            }
        }
    }
}