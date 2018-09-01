package com.joseangelmaneiro.movies.domain

import com.joseangelmaneiro.movies.data.Movie


interface MoviesRepository {

    fun getMovies(handler: Handler<List<Movie>>)

    fun getMovie(movieId: Int, handler: Handler<Movie>)

}