package com.joseangelmaneiro.movies.data.source.local

import com.joseangelmaneiro.movies.data.Handler
import com.joseangelmaneiro.movies.data.Movie


class MoviesLocalDataSourceImpl private constructor(
        private val moviesDatabaseHelper: MoviesDatabaseHelper) : MoviesLocalDataSource {

    companion object {
        private var INSTANCE: MoviesLocalDataSourceImpl? = null

        fun getInstance(moviesDatabaseHelper: MoviesDatabaseHelper): MoviesLocalDataSourceImpl {
            if (INSTANCE == null) {
                INSTANCE = MoviesLocalDataSourceImpl(moviesDatabaseHelper)
            }
            return INSTANCE!!
        }
    }

    override fun getMovies(handler: Handler<List<Movie>>) {
        val movieList = moviesDatabaseHelper.getAllMovies()
        if (!movieList.isEmpty()) {
            handler.handle(movieList)
        } else {
            handler.error()
        }
    }

    override fun getMovie(movieId: Int, handler: Handler<Movie>) {
        val movie = moviesDatabaseHelper.getMovie(movieId)
        if (movie != null) {
            handler.handle(movie)
        } else {
            handler.error()
        }
    }

    override fun saveMovies(movieList: List<Movie>) {
        moviesDatabaseHelper.addMovies(movieList)
    }

    override fun deleteAllMovies() {
        moviesDatabaseHelper.deleteAllMovies()
    }

}
