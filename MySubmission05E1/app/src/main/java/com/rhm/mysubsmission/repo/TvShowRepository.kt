package com.rhm.mysubsmission.repo

import android.util.Log
import com.rhm.mysubsmission.api.GetTvShowResponse
import com.rhm.mysubsmission.api.TvShowApi
import com.rhm.mysubsmission.api.TvShowItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvShowRepository {

    val apiKey: String = "b9f348cb3669613861ca4cd300c88fd4"
    private val apiTvShow: TvShowApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiTvShow = retrofit.create(
            TvShowApi::class.java
        )
    }

    fun getDiscoverTvShows(
        page: Int = 1,
        onSuccess: (tvShow: List<TvShowItems>) -> Unit,
        onError: () -> Unit
    ) {
        apiTvShow.getDiscoverTvShows(apiKey, page = page)
            .enqueue(object : Callback<GetTvShowResponse> {
                override fun onResponse(
                    call: Call<GetTvShowResponse>,
                    response: Response<GetTvShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.tvShows)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetTvShowResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

    fun getSearchTvShows(
        search: String, // = "test",
        onSuccess: (tvShow: List<TvShowItems>) -> Unit,
        onError: () -> Unit
    ) {
        apiTvShow.getSearchTvShows(apiKey, query = search)
            .enqueue(object : Callback<GetTvShowResponse> {
                override fun onResponse(
                    call: Call<GetTvShowResponse>,
                    response: Response<GetTvShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.tvShows)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetTvShowResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

}