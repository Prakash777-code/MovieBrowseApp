package com.example.movieverse.Networks

import com.example.movieverse.Models.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchApi {
    @GET("/")
    suspend fun searchMovie(
        @Query("apikey") apiKey: String,
        @Query("s") title: String
    ): MovieSearchResponse
}