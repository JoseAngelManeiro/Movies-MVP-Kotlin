package com.joseangelmaneiro.movies.data.source.remote

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.entity.PageEntity
import com.joseangelmaneiro.movies.data.exception.NetworkConnectionException
import com.joseangelmaneiro.movies.data.exception.ServiceException
import com.joseangelmaneiro.movies.data.source.remote.net.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO Put here your api key (https://developers.themoviedb.org/3/getting-started)
private const val API_KEY = "8f2e6e734988b4bc5eac72bd3dd62afe"

class MoviesRemoteDataSourceImpl(private val movieService: MovieService): MoviesRemoteDataSource {

    override fun getMovies(handler: Handler<List<MovieEntity>>) {
        movieService.getMovies(API_KEY).enqueue(object : Callback<PageEntity>{
            override fun onResponse(call: Call<PageEntity>?, response: Response<PageEntity>) {
                if(response.isSuccessful){
                    handler.handle(response.body().movies)
                } else{
                    handler.error(ServiceException())
                }
            }

            override fun onFailure(call: Call<PageEntity>?, t: Throwable?) {
                handler.error(NetworkConnectionException())
            }
        })
    }

}