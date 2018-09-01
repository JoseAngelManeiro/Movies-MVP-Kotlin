package com.joseangelmaneiro.movies.data.source.local

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.data.Movie


interface MoviesLocalDataSource {

    fun getMovies(handler: Handler<List<Movie>>)

    fun getMovie(movieId: Int, handler: Handler<Movie>)

    fun saveMovies(movieList: List<Movie>)

    fun deleteAllMovies()

}