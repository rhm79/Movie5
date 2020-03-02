package com.rhm.mysubsmission.repo

import android.util.Log
import com.rhm.mysubsmission.api.GetMovieResponse
import com.rhm.mysubsmission.api.MovieApi
import com.rhm.mysubsmission.api.MovieItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {

    val apiKey: String = "b9f348cb3669613861ca4cd300c88fd4"
    private val apiMovie: MovieApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiMovie = retrofit.create(
            MovieApi::class.java
        )
    }

    fun getDiscoverMovies(
        page: Int = 1,
        onSuccess: (movie: List<MovieItems>) -> Unit,
        onError: () -> Unit
    ) {
        apiMovie.getDiscoverMovies(page = page)
            .enqueue(object : Callback<GetMovieResponse> {
                override fun onResponse(
                    call: Call<GetMovieResponse>,
                    response: Response<GetMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

    fun getSearchMovies(
        search: String, // = "test",
        onSuccess: (tvShow: List<MovieItems>) -> Unit,
        onError: () -> Unit
    ) {
        apiMovie.getSearchMovies(apiKey, query = search)
            .enqueue(object : Callback<GetMovieResponse> {
                override fun onResponse(
                    call: Call<GetMovieResponse>,
                    response: Response<GetMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

}