package com.joseangelmaneiro.movies.ui.list

import com.joseangelmaneiro.movies.ui.BaseView


interface MovieListView : BaseView {

    fun refreshList()

    fun cancelRefreshDialog()

    fun navigateToDetailScreen(movieId: Int)

}