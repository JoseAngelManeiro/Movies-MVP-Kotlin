package com.joseangelmaneiro.movies.ui.detail

import com.joseangelmaneiro.movies.ui.BaseView


interface DetailMovieView : BaseView {

    fun displayImage(url: String)

    fun displayTitle(title: String)

    fun displayVoteAverage(voteAverage: String)

    fun displayReleaseDate(releaseDate: String)

    fun displayOverview(overview: String)

    fun goToBack()

}