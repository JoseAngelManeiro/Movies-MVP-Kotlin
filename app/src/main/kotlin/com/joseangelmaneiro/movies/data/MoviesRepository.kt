package com.joseangelmaneiro.movies.data


interface MoviesRepository {

    fun getMovies(handler: Handler<List<Movie>>)

    fun getMovie(movieId: Int, handler: Handler<Movie>)

}