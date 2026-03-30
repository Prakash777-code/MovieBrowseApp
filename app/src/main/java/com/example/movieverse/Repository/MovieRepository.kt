package com.example.movieverse.Repository

import com.example.movieverse.BuildConfig
import com.example.movieverse.Models.MovieDetailResponse
import com.example.movieverse.Models.MovieSearchResponse
import com.example.movieverse.Networks.ApiClient

class MovieRepository {

    suspend fun getMovieSearched( title: String): MovieSearchResponse {

        return ApiClient.movieSearchApi.searchMovie(BuildConfig.APP_API_KEY, title)
    }

    suspend fun fetchMovieDetails( imdbID: String): MovieDetailResponse {

        return ApiClient.movieDetailApi.getMovieDetails(BuildConfig.APP_API_KEY, imdbID)
    }
}