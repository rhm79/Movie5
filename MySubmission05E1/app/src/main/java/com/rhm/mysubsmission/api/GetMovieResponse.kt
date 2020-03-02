package com.rhm.mysubsmission.api

import com.google.gson.annotations.SerializedName

data class GetMovieResponse(
    @SerializedName("results") val movies: List<MovieItems>
)