package com.joseangelmaneiro.movies.data.source.remote.net

import com.joseangelmaneiro.movies.data.Page
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<Page>

}