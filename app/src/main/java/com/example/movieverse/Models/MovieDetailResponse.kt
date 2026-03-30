package com.example.movieverse.Models

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(

    @SerializedName("Title") val Title: String?,
    @SerializedName("Year") val Year: String?,
    @SerializedName("Released") val Released: String?,
    @SerializedName("Runtime") val RunTime: String?,
    @SerializedName("Genre") val Genre: String?,
    @SerializedName("Director") val Director: String?,
    @SerializedName("Writer") val Writer: String?,
    @SerializedName("Actors") val Actors: String?,
    @SerializedName("Plot") val Plot: String?,
    @SerializedName("Language") val Language: String?,
    @SerializedName("Country") val Country: String?,
    @SerializedName("Awards") val Awards: String?,
    @SerializedName("Poster") val Poster: String?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("imdbVotes") val imdbVotes: String?,
    @SerializedName("imdbID") val imdbID: String?,
    @SerializedName("Type") val Type: String?,
    @SerializedName("BoxOffice") val BoxOffice: String?,
    @SerializedName("Production") val Production: String?
)