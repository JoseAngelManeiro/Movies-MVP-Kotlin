package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource


class MoviesRepositoryImpl private constructor(
        private val localDataSource: MoviesLocalDataSource,
        private val remoteDataSource: MoviesRemoteDataSource): MoviesRepository {

    companion object {
        private var INSTANCE: MoviesRepository? = null

        fun getInstance(localDataSource: MoviesLocalDataSource,
                        remoteDataSource: MoviesRemoteDataSource): MoviesRepository{
            if(INSTANCE==null){
                INSTANCE = MoviesRepositoryImpl(localDataSource, remoteDataSource)
            }
            return INSTANCE!!
        }
    }

    override fun getMovies(handler: Handler<List<Movie>>) {
        remoteDataSource.getMovies(object : Handler<List<Movie>>{
            override fun handle(movieList: List<Movie>) {
                localDataSource.deleteAllMovies()
                localDataSource.saveMovies(movieList)
                handler.handle(movieList)
            }

            override fun error() {
                handler.error()
            }
        })
    }

    override fun getMovie(movieId: Int, handler: Handler<Movie>) {
        localDataSource.getMovie(movieId, object : Handler<Movie>{
            override fun handle(movie: Movie) {
                handler.handle(movie)
            }

            override fun error() {
                handler.error()
            }
        })
    }

}