package com.joseangelmaneiro.movies.ui.detail

import android.os.Bundle
import android.view.MenuItem
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.di.Injection
import com.joseangelmaneiro.movies.ui.BaseActivity
import com.joseangelmaneiro.movies.ui.Formatter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail_movie.*


class DetailMovieActivity : BaseActivity(), DetailMovieView {

    companion object {
        const val EXTRA_MOVIE_ID = "MOVIE_ID"
    }

    private lateinit var presenter: DetailMoviePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        setUpPresenter()

        setUpActionBar()

        informPresenterViewIsReady()
    }

    private fun setUpPresenter() {
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        presenter = DetailMoviePresenter(
                Injection.provideRepository(applicationContext),
                Formatter(),
                movieId)
        presenter.setView(this)
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun informPresenterViewIsReady() {
        presenter.viewReady()
    }

    override fun displayImage(url: String) {
        Picasso.with(this)
                .load(url)
                .into(image_movie)
    }

    override fun displayTitle(title: String) {
        setTitle(title)
    }

    override fun displayVoteAverage(voteAverage: String) {
        text_voteAverage.text = voteAverage
    }

    override fun displayReleaseDate(releaseDate: String) {
        text_releaseDate.text = releaseDate
    }

    override fun displayOverview(overview: String) {
        text_overview.text = overview
    }

    override fun goToBack() {
        onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                presenter.navUpSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
