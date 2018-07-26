package com.joseangelmaneiro.movies.di

import android.content.Context
import com.joseangelmaneiro.movies.data.MoviesRepository
import com.joseangelmaneiro.movies.data.MoviesRepositoryImpl
import com.joseangelmaneiro.movies.data.source.local.MoviesDatabaseHelper
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSourceImpl
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSourceImpl
import com.joseangelmaneiro.movies.data.source.remote.net.RetrofitClient


class Injection {

    companion object {
        fun provideRepository(context: Context): MoviesRepository {
            return MoviesRepositoryImpl.getInstance(getLocalDataSource(context),
                    getRemoteDataSource())
        }

        private fun getLocalDataSource(context: Context): MoviesLocalDataSource {
            return MoviesLocalDataSourceImpl.getInstance(MoviesDatabaseHelper.getInstance(context))
        }

        private fun getRemoteDataSource(): MoviesRemoteDataSource {
            return MoviesRemoteDataSourceImpl.getInstance(RetrofitClient.INSTANCE)
        }
    }

}