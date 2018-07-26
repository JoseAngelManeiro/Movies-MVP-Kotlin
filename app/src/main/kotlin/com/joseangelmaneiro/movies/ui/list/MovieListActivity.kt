package com.joseangelmaneiro.movies.ui.list

import android.content.Intent
import android.os.Bundle
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.di.Injection
import com.joseangelmaneiro.movies.ui.BaseActivity
import com.joseangelmaneiro.movies.ui.Formatter
import com.joseangelmaneiro.movies.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.activity_movie_list.*


class MovieListActivity : BaseActivity(), MovieListView {

    private lateinit var presenter: MovieListPresenter

    private lateinit var adapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setUpPresenter()

        setUpActionBar()

        setUpListView()

        setUpRefreshView()

        informPresenterViewIsReady()
    }

    private fun setUpPresenter() {
        presenter = MovieListPresenter(Injection.provideRepository(applicationContext),
                Formatter())
        presenter.setView(this)
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
    }

    private fun setUpListView() {
        adapter = MoviesAdapter(presenter)
        recyclerView.adapter = adapter
    }

    private fun setUpRefreshView() {
        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent)
        refreshLayout.setOnRefreshListener { presenter.refresh() }
    }

    private fun informPresenterViewIsReady() {
        presenter.viewReady()
    }

    override fun refreshList() {
        adapter.refreshData()
    }

    override fun cancelRefreshDialog() {
        refreshLayout.isRefreshing = false
    }

    override fun navigateToDetailScreen(movieId: Int) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, movieId)
        startActivity(intent)
    }

}
