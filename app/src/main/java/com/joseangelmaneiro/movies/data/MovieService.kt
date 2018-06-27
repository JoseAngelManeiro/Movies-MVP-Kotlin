package com.joseangelmaneiro.movies.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {

    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<Page>

}