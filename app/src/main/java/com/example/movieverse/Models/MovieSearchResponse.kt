package com.example.movieverse.Models

import com.example.movieverse.Models.Search
import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Search") val search: List<Search>?,
    @SerializedName("Response") val response: String?,
    @SerializedName("Error") val error: String?
)