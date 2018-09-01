package com.joseangelmaneiro.movies.data.source.local

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.data.entity.MovieEntity


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

    override fun getMovies(handler: Handler<List<MovieEntity>>) {
        val movieList = moviesDatabaseHelper.getAllMovies()
        if (!movieList.isEmpty()) {
            handler.handle(movieList)
        } else {
            handler.error()
        }
    }

    override fun getMovie(movieId: Int, handler: Handler<MovieEntity>) {
        val movie = moviesDatabaseHelper.getMovie(movieId)
        if (movie != null) {
            handler.handle(movie)
        } else {
            handler.error()
        }
    }

    override fun saveMovies(movieEntityList: List<MovieEntity>) {
        moviesDatabaseHelper.addMovies(movieEntityList)
    }

    override fun deleteAllMovies() {
        moviesDatabaseHelper.deleteAllMovies()
    }

}
