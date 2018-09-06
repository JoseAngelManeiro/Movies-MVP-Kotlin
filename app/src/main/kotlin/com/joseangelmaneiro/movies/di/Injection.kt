package com.joseangelmaneiro.movies.di

import android.content.Context
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.data.MoviesRepositoryImpl
import com.joseangelmaneiro.movies.data.entity.mapper.EntityDataMapper
import com.joseangelmaneiro.movies.data.source.local.MoviesDatabaseHelper
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSourceImpl
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSourceImpl
import com.joseangelmaneiro.movies.data.source.remote.net.RetrofitClient
import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory


class Injection {

    companion object {
        fun provideUseCaseFactory(context: Context): UseCaseFactory{
            return UseCaseFactory(provideRepository(context))
        }

        private fun provideRepository(context: Context): MoviesRepository {
            return MoviesRepositoryImpl.getInstance(getLocalDataSource(context),
                    getRemoteDataSource(), EntityDataMapper())
        }

        private fun getLocalDataSource(context: Context): MoviesLocalDataSource {
            return MoviesLocalDataSourceImpl.getInstance(MoviesDatabaseHelper.getInstance(context))
        }

        private fun getRemoteDataSource(): MoviesRemoteDataSource {
            return MoviesRemoteDataSourceImpl.getInstance(RetrofitClient.INSTANCE)
        }
    }

}