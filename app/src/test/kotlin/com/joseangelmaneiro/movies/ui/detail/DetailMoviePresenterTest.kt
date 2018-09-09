package com.joseangelmaneiro.movies.ui.detail

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.UseCase
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
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
    private lateinit var useCaseFactory: UseCaseFactory
    @Mock
    private lateinit var useCase: UseCase<Movie, GetMovie.Params>
    @Mock
    private lateinit var formatter: Formatter
    @Mock
    private lateinit var view: DetailMovieView
    private val movieHandlerCaptor = argumentCaptor<Handler<Movie>>()
    private val paramsCaptor = argumentCaptor<GetMovie.Params>()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = DetailMoviePresenter(useCaseFactory, formatter, MOVIE_ID)
        sut.setView(view)

        whenever(useCaseFactory.getMovie()).thenReturn(useCase)
    }

    @Test
    fun viewReady_InvokesUseCase() {
        sut.viewReady()

        verify(useCase).execute(any(), paramsCaptor.capture())
        assertEquals(MOVIE_ID, paramsCaptor.firstValue.movieId)
    }

    @Test
    fun viewReady_InvokesDisplayImage() {
        val fakePath = "fake-path"
        whenever(formatter.getCompleteUrlImage(anyString())).thenReturn(fakePath)
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayImage(eq(fakePath))
    }

    @Test
    fun viewReady_InvokesDisplayTitle() {
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayTitle(eq(movie.title))
    }

    @Test
    fun viewReady_InvokesDisplayVoteAverage() {
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayVoteAverage(eq(movie.voteAverage))
    }

    @Test
    fun viewReady_InvokesDisplayReleaseDate() {
        val fakeDate = "22/10/2017"
        whenever(formatter.formatDate(anyString())).thenReturn(fakeDate)
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayReleaseDate(eq(fakeDate))
    }

    @Test
    fun viewReady_InvokesDisplayOverview() {
        sut.viewReady()
        setMovieAvailable(movie)

        verify(view).displayOverview(eq(movie.overview))
    }

    @Test
    fun navUpSelected_InvokesGoToBack() {
        sut.navUpSelected()

        verify(view).goToBack()
    }


    private fun setMovieAvailable(movie: Movie) {
        verify(useCase).execute(movieHandlerCaptor.capture(), any())
        movieHandlerCaptor.firstValue.handle(movie)
    }

}