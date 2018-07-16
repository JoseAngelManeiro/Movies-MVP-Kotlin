package com.joseangelmaneiro.movies.ui.list

import com.joseangelmaneiro.movies.data.Handler
import com.joseangelmaneiro.movies.data.Movie
import com.joseangelmaneiro.movies.data.MoviesRepository
import com.joseangelmaneiro.movies.ui.Formatter
import java.lang.ref.WeakReference


class MovieListPresenter(private val repository: MoviesRepository,
                         private val formatter: Formatter) : Handler<List<Movie>> {

    private lateinit var view: WeakReference<MovieListView>

    private var movieList = emptyList<Movie>()

    private var selectedMovieId: Int = 0


    fun setView(movieListView: MovieListView) {
        view = WeakReference(movieListView)
    }

    fun viewReady() {
        invokeGetMovies()
    }

    fun refresh() {
        invokeGetMovies()
    }

    fun invokeGetMovies() {
        repository.getMovies(this)
    }

    override fun handle(movieList: List<Movie>) {
        saveMovies(movieList)
        view.get()?.let {
            it.cancelRefreshDialog()
            it.refreshList()
        }
    }

    override fun error() {
        view.get()?.let {
            it.cancelRefreshDialog()
            it.showErrorMessage()
        }
    }

    fun getItemsCount(): Int{
        return if(moviesListIsEmpty()) 0 else movieList.size
    }

    fun configureCell(movieCellView: MovieCellView, position: Int) {
        val movie = getMovie(position)
        movieCellView.displayImage(formatter.getCompleteUrlImage(movie.posterPath))
    }

    fun onItemClick(position: Int) {
        val movie = getMovie(position)
        saveSelectedMovieId(movie.id)
        view.get()?.navigateToDetailScreen(selectedMovieId)
    }

    fun saveMovies(movieList: List<Movie>) {
        this.movieList = movieList
    }

    fun getMovie(position: Int): Movie {
        return movieList[position]
    }

    fun saveSelectedMovieId(selectedMovieId: Int) {
        this.selectedMovieId = selectedMovieId
    }

    fun moviesListIsEmpty(): Boolean {
        return movieList.isEmpty()
    }

}