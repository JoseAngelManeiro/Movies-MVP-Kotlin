package com.joseangelmaneiro.movies.domain

import com.joseangelmaneiro.movies.data.entity.MovieEntity


interface MoviesRepository {

    fun getMovies(handler: Handler<List<MovieEntity>>)

    fun getMovie(movieId: Int, handler: Handler<MovieEntity>)

}