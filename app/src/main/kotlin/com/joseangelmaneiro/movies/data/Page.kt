package com.joseangelmaneiro.movies.data

import com.google.gson.annotations.SerializedName


data class Page(
        @SerializedName("page") val page: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("results") val movies: List<Movie>)