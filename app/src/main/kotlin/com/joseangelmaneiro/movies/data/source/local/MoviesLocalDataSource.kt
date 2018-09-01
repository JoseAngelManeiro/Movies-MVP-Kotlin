package com.joseangelmaneiro.movies.data.source.local

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.data.entity.MovieEntity


interface MoviesLocalDataSource {

    fun getMovies(handler: Handler<List<MovieEntity>>)

    fun getMovie(movieId: Int, handler: Handler<MovieEntity>)

    fun saveMovies(movieEntityList: List<MovieEntity>)

    fun deleteAllMovies()

}