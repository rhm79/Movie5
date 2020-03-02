package com.rhm.mysubsmission.api

import com.google.gson.annotations.SerializedName

data class GetTvShowResponse(
    @SerializedName("results") val tvShows: List<TvShowItems>
)