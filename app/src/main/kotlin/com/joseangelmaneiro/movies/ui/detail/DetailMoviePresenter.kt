package com.joseangelmaneiro.movies.ui.detail

import com.joseangelmaneiro.movies.data.Handler
import com.joseangelmaneiro.movies.data.Movie
import com.joseangelmaneiro.movies.data.MoviesRepository
import com.joseangelmaneiro.movies.ui.Formatter
import java.lang.ref.WeakReference


class DetailMoviePresenter(private val repository: MoviesRepository,
                           private val formatter: Formatter,
                           private val movieId: Int) : Handler<Movie> {

    private lateinit var view: WeakReference<DetailMovieView>
    

    fun setView(detailMovieView: DetailMovieView) {
        view = WeakReference(detailMovieView)
    }

    fun viewReady() {
        repository.getMovie(movieId, this)
    }

    override fun handle(movie: Movie) {
        view.get()?.let { detailMovieView ->
            detailMovieView.displayImage(formatter.getCompleteUrlImage(movie.backdropPath))
            detailMovieView.displayTitle(movie.title)
            detailMovieView.displayVoteAverage(movie.voteAverage)
            detailMovieView.displayReleaseDate(formatter.formatDate(movie.releaseDate))
            detailMovieView.displayOverview(movie.overview)
        }
    }

    override fun error() {
        view.get()?.showErrorMessage()
    }

    fun navUpSelected() {
        view.get()?.goToBack()
    }

}