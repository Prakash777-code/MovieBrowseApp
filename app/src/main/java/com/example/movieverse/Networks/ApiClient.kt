package com.example.movieverse.Networks

import com.example.movieverse.Utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val movieSearchApi: MovieSearchApi by lazy {
        retrofit.create(MovieSearchApi::class.java)
    }

    val movieDetailApi: MovieDetailApi by lazy {
        retrofit.create(MovieDetailApi::class.java)
    }
}