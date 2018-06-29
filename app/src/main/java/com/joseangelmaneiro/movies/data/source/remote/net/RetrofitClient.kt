package com.joseangelmaneiro.movies.data.source.remote.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

class RetrofitClient {

    companion object {
        val INSTANCE: MovieService by lazy {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            retrofit.create(MovieService::class.java)
        }

    }

}