package com.joseangelmaneiro.movies.platform

import android.app.Activity
import com.joseangelmaneiro.movies.ui.detail.DetailMovieActivity


fun Activity.navigateToDetail(movieId: Int){
    DetailMovieActivity.launch(this, movieId)
}