package com.joseangelmaneiro.movies.ui.detail

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.presentation.presenters.DetailMoviePresenter
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations


private const val MOVIE_ID = 1234

class DetailMoviePresenterTest {

    private val movie = TestUtils.createMovie()

    private lateinit var sut: DetailMoviePresenter
    @Mock
    private lateinit var repository: MoviesRepository
    @Mock
    private lateinit var formatter: Formatter
    @Mock
    private lateinit var view: DetailMovieView
    private val movieHandlerCaptor = argumentCaptor<Handler<Movie>>()
    private val textCaptor = argumentCaptor<String>()
    private val intCaptor = argumentCaptor<Int>()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = DetailMoviePresenter(repository, formatter, MOVIE_ID)
        sut.setView(view)
    }

    @Test
    fun viewReady_InvokesGetMovie() {
        sut.viewReady()

        verify(repository).getMovie(intCaptor.capture(), any())
        assertEquals(MOVIE_ID.toLong(), intCaptor.firstValue.toLong())
    }

    @Test
    fun viewReady_InvokesDisplayImage() {
        val fakePath = "fake-path"
        whenever(formatter.getCompleteUrlImage(anyString())).thenReturn(fakePath)
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayImage(textCaptor.capture())
        assertEquals(fakePath, textCaptor.firstValue)
    }

    @Test
    fun viewReady_InvokesDisplayTitle() {
        val titleExpected = movie.title
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayTitle(textCaptor.capture())
        assertEquals(titleExpected, textCaptor.firstValue)
    }

    @Test
    fun viewReady_InvokesDisplayVoteAverage() {
        val voteAverageExpected = movie.voteAverage
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayVoteAverage(textCaptor.capture())
        assertEquals(voteAverageExpected, textCaptor.firstValue)
    }

    @Test
    fun viewReady_InvokesDisplayReleaseDate() {
        val fakeDate = "22/10/2017"
        whenever(formatter.formatDate(anyString())).thenReturn(fakeDate)
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayReleaseDate(textCaptor.capture())
        assertEquals(fakeDate, textCaptor.firstValue)
    }

    @Test
    fun viewReady_InvokesDisplayOverview() {
        val overviewExpected = movie.overview
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayOverview(textCaptor.capture())
        assertEquals(overviewExpected, textCaptor.firstValue)
    }

    @Test
    fun viewReady_FiresErrorMessage() {
        sut.viewReady()
        setMoviesError()

        verify(view).showErrorMessage()
    }

    @Test
    fun navUpSelected_InvokesGoToBack() {
        sut.navUpSelected()

        verify(view).goToBack()
    }


    private fun setMovieAvailable(movie: Movie) {
        verify(repository).getMovie(eq(MOVIE_ID), movieHandlerCaptor.capture())
        movieHandlerCaptor.firstValue.handle(movie)
    }

    private fun setMoviesError() {
        verify(repository).getMovie(eq(MOVIE_ID), movieHandlerCaptor.capture())
        movieHandlerCaptor.firstValue.error()
    }

}