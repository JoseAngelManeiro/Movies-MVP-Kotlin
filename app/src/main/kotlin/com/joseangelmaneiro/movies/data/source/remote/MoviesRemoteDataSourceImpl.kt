package com.joseangelmaneiro.movies.data.source.remote

import com.joseangelmaneiro.movies.data.Handler
import com.joseangelmaneiro.movies.data.Movie
import com.joseangelmaneiro.movies.data.Page
import com.joseangelmaneiro.movies.data.source.remote.net.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO Put here your api key (https://developers.themoviedb.org/3/getting-started)
private const val API_KEY = ""

class MoviesRemoteDataSourceImpl private constructor(
        private val movieService: MovieService): MoviesRemoteDataSource {

    companion object {
        private var INSTANCE: MoviesRemoteDataSource? = null

        fun getInstance(movieService: MovieService): MoviesRemoteDataSource{
            if(INSTANCE==null){
                INSTANCE = MoviesRemoteDataSourceImpl(movieService)
            }
            return INSTANCE!!
        }
    }

    override fun getMovies(handler: Handler<List<Movie>>) {
        movieService.getMovies(API_KEY).enqueue(object : Callback<Page>{
            override fun onResponse(call: Call<Page>?, response: Response<Page>?) {
                val page = response?.body()
                if(page!=null){
                    handler.handle(page.movies)
                } else{
                    handler.error()
                }
            }

            override fun onFailure(call: Call<Page>?, t: Throwable?) {
                handler.error()
            }
        })
    }

}