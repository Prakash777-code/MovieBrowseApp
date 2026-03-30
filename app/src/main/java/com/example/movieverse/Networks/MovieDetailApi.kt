package com.example.movieverse.Networks

import com.example.movieverse.Models.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailApi {
    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbID: String
    ): MovieDetailResponse
}