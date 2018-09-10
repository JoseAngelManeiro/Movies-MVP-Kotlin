package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository


class GetMovie(private val repository: MoviesRepository): UseCase<Movie, GetMovie.Params> {

    override fun execute(handler: Handler<Movie>, params: Params) {
        repository.getMovie(params.movieId, object : Handler<Movie>{
            override fun handle(movie: Movie) {
                handler.handle(movie)
            }

            override fun error(exception: Exception) {
                handler.error(exception)
            }

        })
    }

    class Params(val movieId: Int)
}

