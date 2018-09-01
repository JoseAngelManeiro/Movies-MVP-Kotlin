package com.joseangelmaneiro.movies

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.entity.PageEntity
import java.util.ArrayList


class TestUtils {

    companion object {

        fun createMainPage(): PageEntity {
            return PageEntity(
                    MAIN_NUM_PAGE,
                    MAIN_TOTAL_RESULTS,
                    MAIN_TOTAL_PAGES,
                    createMovieList(MAIN_NUM_MOVIES))
        }

        fun createAltPage(): PageEntity {
            return PageEntity(
                    ALT_NUM_PAGE,
                    ALT_TOTAL_RESULTS,
                    ALT_TOTAL_PAGES,
                    createMovieList(ALT_NUM_MOVIES))
        }

        fun createMainMovie(): MovieEntity {
            return MovieEntity(VOTE_COUNT, MAIN_MOVIE_ID, MOVIE_VIDEO, MAIN_VOTE_AVERAGE, MAIN_MOVIE_TITLE,
                    MOVIE_POPULARITY, POSTER_PATH, MAIN_ORIGINAL_LANGUAGE, MAIN_ORIGINAL_TITLE,
                    GENRE_IDS, BACKDROPPATH, ADULT, OVERVIEW, MAIN_RELEASE_DATE)
        }

        fun createAltMovie(): MovieEntity {
            return MovieEntity(VOTE_COUNT, ALT_MOVIE_ID, MOVIE_VIDEO, ALT_VOTE_AVERAGE, ALT_MOVIE_TITLE,
                    MOVIE_POPULARITY, POSTER_PATH, ALT_ORIGINAL_LANGUAGE, ALT_ORIGINAL_TITLE,
                    GENRE_IDS, BACKDROPPATH, ADULT, OVERVIEW, ALT_RELEASE_DATE)
        }

        fun createMainMovieList(): List<MovieEntity> {
            return createMovieList(10)
        }

        fun createMovieList(numMovies: Int): List<MovieEntity> {
            val movies = ArrayList<MovieEntity>()
            for (i in 0 until numMovies) {
                val movie = MovieEntity(VOTE_COUNT, i, MOVIE_VIDEO, MAIN_VOTE_AVERAGE, MAIN_MOVIE_TITLE,
                        MOVIE_POPULARITY, POSTER_PATH, MAIN_ORIGINAL_LANGUAGE, MAIN_ORIGINAL_TITLE,
                        GENRE_IDS, BACKDROPPATH, ADULT, OVERVIEW, MAIN_RELEASE_DATE)
                movies.add(movie)
            }
            return movies
        }

    }

}

private const val MAIN_NUM_PAGE = 1
private const val MAIN_TOTAL_PAGES = 2
private const val MAIN_TOTAL_RESULTS = 40
private const val MAIN_NUM_MOVIES = 20

private const val ALT_NUM_PAGE = 2
private const val ALT_TOTAL_PAGES = 2
private const val ALT_TOTAL_RESULTS = 40
private const val ALT_NUM_MOVIES = 20

private const val MAIN_MOVIE_ID = 1
private const val VOTE_COUNT = 5
private const val MOVIE_VIDEO = true
private const val MAIN_VOTE_AVERAGE = "6.2"
private const val MAIN_MOVIE_TITLE = "Movie One"
private const val MOVIE_POPULARITY = 5f
private const val POSTER_PATH = "fake_poster_path.png"
private const val MAIN_ORIGINAL_LANGUAGE = "ES"
private const val MAIN_ORIGINAL_TITLE = "main_title"
private val GENRE_IDS: List<Int>? = null
private const val BACKDROPPATH = "fake_backdroppath.png"
private const val ADULT = false
private const val OVERVIEW = "Overview"
private const val MAIN_RELEASE_DATE = "05/12/2017"

private const val ALT_MOVIE_ID = 2
private const val ALT_MOVIE_TITLE = "Movie Two"
private const val ALT_ORIGINAL_LANGUAGE = "EN"
private const val ALT_ORIGINAL_TITLE = "alt_title"
private const val ALT_RELEASE_DATE = "06/12/2017"
private const val ALT_VOTE_AVERAGE = "4.3"