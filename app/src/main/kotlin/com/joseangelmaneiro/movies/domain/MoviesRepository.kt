package com.joseangelmaneiro.movies.domain


interface MoviesRepository {

    fun getMovies(handler: Handler<List<Movie>>)

    fun getMovie(movieId: Int, handler: Handler<Movie>)

}