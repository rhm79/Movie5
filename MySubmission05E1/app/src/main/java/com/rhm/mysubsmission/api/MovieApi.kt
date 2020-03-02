package com.rhm.mysubsmission.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getDiscoverMovies(
        @Query("api_key") apiKey: String = "b9f348cb3669613861ca4cd300c88fd4",
        @Query("page") page: Int
    ): Call<GetMovieResponse>

    @GET("search/movie")
    fun getSearchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<GetMovieResponse>
}