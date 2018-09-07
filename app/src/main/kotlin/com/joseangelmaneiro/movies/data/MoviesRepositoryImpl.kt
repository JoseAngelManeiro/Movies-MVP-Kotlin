package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.entity.mapper.EntityDataMapper
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository


class MoviesRepositoryImpl private constructor(
        private val localDataSource: MoviesLocalDataSource,
        private val remoteDataSource: MoviesRemoteDataSource,
        private val entityDataMapper: EntityDataMapper): MoviesRepository {

    companion object {
        private var INSTANCE: MoviesRepositoryImpl? = null

        fun getInstance(localDataSource: MoviesLocalDataSource,
                        remoteDataSource: MoviesRemoteDataSource,
                        entityDataMapper: EntityDataMapper): MoviesRepositoryImpl{
            if(INSTANCE==null){
                INSTANCE = MoviesRepositoryImpl(localDataSource, remoteDataSource, entityDataMapper)
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getMovies(handler: Handler<List<Movie>>) {
        remoteDataSource.getMovies(object : Handler<List<MovieEntity>> {
            override fun handle(movieEntityList: List<MovieEntity>) {
                localDataSource.deleteAllMovies()
                localDataSource.saveMovies(movieEntityList)
                handler.handle(entityDataMapper.transform(movieEntityList))
            }

            override fun error() {
                handler.error()
            }
        })
    }

    override fun getMovie(movieId: Int, handler: Handler<Movie>) {
        handler.handle(entityDataMapper.transform(localDataSource.getMovie(movieId))!!)
    }

}