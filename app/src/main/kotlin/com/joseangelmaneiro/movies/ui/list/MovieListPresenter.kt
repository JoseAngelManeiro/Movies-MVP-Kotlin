package com.joseangelmaneiro.movies.ui.list

import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.ui.Formatter
import java.lang.ref.WeakReference


class MovieListPresenter(private val repository: MoviesRepository,
                         private val formatter: Formatter) : Handler<List<MovieEntity>> {

    private lateinit var view: WeakReference<MovieListView>

    private var movieList = emptyList<MovieEntity>()

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

    override fun handle(movieList: List<MovieEntity>) {
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
        view.get()?.navigateToDetailScreen(getSelectedMovieId())
    }

    fun saveMovies(movieList: List<MovieEntity>) {
        this.movieList = movieList
    }

    private fun getMovie(position: Int): MovieEntity {
        return movieList[position]
    }

    private fun saveSelectedMovieId(selectedMovieId: Int) {
        this.selectedMovieId = selectedMovieId
    }

    fun moviesListIsEmpty(): Boolean {
        return movieList.isEmpty()
    }

    fun getSelectedMovieId(): Int {
        return selectedMovieId
    }

}