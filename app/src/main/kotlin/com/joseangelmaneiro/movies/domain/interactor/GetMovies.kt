package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.UseCase


class GetMovies(private val repository: MoviesRepository): UseCase<List<Movie>, Unit>{

    override fun execute(handler: Handler<List<Movie>>, unused: Unit) {
        repository.getMovies(object : Handler<List<Movie>>{
            override fun handle(movies: List<Movie>) {
                handler.handle(movies)
            }

            override fun error() {
                handler.error()
            }

        })
    }

}

