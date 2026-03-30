package com.example.movieverse.Models

import com.google.gson.annotations.SerializedName

data class Ratings(

    @SerializedName ("source") val source: String?,
    @SerializedName("value") val value: String?
)