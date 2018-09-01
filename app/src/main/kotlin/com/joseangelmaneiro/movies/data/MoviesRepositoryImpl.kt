package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.MoviesRepository


class MoviesRepositoryImpl private constructor(
        private val localDataSource: MoviesLocalDataSource,
        private val remoteDataSource: MoviesRemoteDataSource): MoviesRepository {

    companion object {
        private var INSTANCE: MoviesRepositoryImpl? = null

        fun getInstance(localDataSource: MoviesLocalDataSource,
                        remoteDataSource: MoviesRemoteDataSource): MoviesRepositoryImpl{
            if(INSTANCE==null){
                INSTANCE = MoviesRepositoryImpl(localDataSource, remoteDataSource)
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getMovies(handler: Handler<List<MovieEntity>>) {
        remoteDataSource.getMovies(object : Handler<List<MovieEntity>> {
            override fun handle(movieList: List<MovieEntity>) {
                localDataSource.deleteAllMovies()
                localDataSource.saveMovies(movieList)
                handler.handle(movieList)
            }

            override fun error() {
                handler.error()
            }
        })
    }

    override fun getMovie(movieId: Int, handler: Handler<MovieEntity>) {
        localDataSource.getMovie(movieId, object : Handler<MovieEntity> {
            override fun handle(movieEntity: MovieEntity) {
                handler.handle(movieEntity)
            }

            override fun error() {
                handler.error()
            }
        })
    }

}