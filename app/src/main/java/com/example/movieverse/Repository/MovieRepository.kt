package com.example.movieverse.Repository

import com.example.movieverse.Models.MovieDetailResponse
import com.example.movieverse.Models.MovieSearchResponse
import com.example.movieverse.Networks.ApiClient

class MovieRepository {

    suspend fun getMovieSearched(
        apiKey: String,
        title: String
    ): MovieSearchResponse {
        return ApiClient.movieSearchApi.searchMovie(apiKey, title)
    }

    suspend fun fetchMovieDetails(
        apiKey: String,
        imdbID: String
    ): MovieDetailResponse {
        return ApiClient.movieDetailApi.getMovieDetails(apiKey, imdbID)
    }
}