package com.joseangelmaneiro.movies.data.source.remote

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.data.entity.MovieEntity


interface MoviesRemoteDataSource {

    fun getMovies(handler: Handler<List<MovieEntity>>)

}