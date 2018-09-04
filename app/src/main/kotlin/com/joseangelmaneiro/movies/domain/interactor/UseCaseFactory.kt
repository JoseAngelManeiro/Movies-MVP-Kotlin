package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.UseCase


class UseCaseFactory(private val repository: MoviesRepository) {

    fun getMovie(): UseCase<Movie, GetMovie.Params> {
        return GetMovie(repository)
    }

    fun getMovies(): UseCase<List<Movie>, Unit> {
        return GetMovies(repository)
    }

}