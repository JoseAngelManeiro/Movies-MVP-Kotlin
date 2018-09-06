package com.joseangelmaneiro.movies.ui.list

import com.joseangelmaneiro.movies.DEFAULT_SIZE_LIST
import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.UseCase
import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
import com.joseangelmaneiro.movies.presentation.MovieCellView
import com.joseangelmaneiro.movies.presentation.presenters.MovieListPresenter
import com.joseangelmaneiro.movies.presentation.MovieListView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations


const val URL_TO_DISPLAY = "fake_url"

class MovieListPresenterTest {

    private val defaultMovieList = TestUtils.createDefaultMovieList()

    private lateinit var sut: MovieListPresenter
    @Mock
    private lateinit var useCaseFactory: UseCaseFactory
    @Mock
    private lateinit var useCase: UseCase<List<Movie>, Unit>
    @Mock
    private lateinit var formatter: Formatter
    @Mock
    private lateinit var view: MovieListView
    @Mock
    private lateinit var cellView: MovieCellView
    private val moviesHandlerCaptor = argumentCaptor<Handler<List<Movie>>>()
    private val textCaptor = argumentCaptor<String>()
    private val intCaptor = argumentCaptor<Int>()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = MovieListPresenter(useCaseFactory, formatter)
        sut.setView(view)

        whenever(useCaseFactory.getMovies()).thenReturn(useCase)
        whenever(formatter.getCompleteUrlImage(anyString())).thenReturn(URL_TO_DISPLAY)
    }

    @Test
    fun viewReady_InvokesGetMovies() {
        sut.viewReady()

        verify(useCase).execute(any(), eq(Unit))
    }

    @Test
    fun refresh_InvokesGetMovies() {
        sut.refresh()

        verify(useCase).execute(any(), eq(Unit))
    }

    @Test
    fun invokeGetMovies_SavesMovies() {
        // The list is empty when starting
        assertTrue(sut.moviesListIsEmpty())

        sut.invokeGetMovies()
        setMoviesAvailable(defaultMovieList)

        // If repository returns movies, they are saved
        assertFalse(sut.moviesListIsEmpty())
    }

    @Test
    fun invokeGetMovies_RefreshesView() {
        sut.invokeGetMovies()
        setMoviesAvailable(defaultMovieList)

        verify(view).cancelRefreshDialog()
        verify(view).refreshList()
    }

    @Test
    fun invokeGetMovies_ShowsError() {
        sut.invokeGetMovies()
        setMoviesError()

        verify(view).cancelRefreshDialog()
        verify(view).showErrorMessage()
    }

    @Test
    fun getItemsCount_ReturnsZeroWhenThereIsNoData() {
        assertEquals(0, sut.getItemsCount().toLong())
    }

    @Test
    fun getItemsCount_ReturnsNumberOfItems() {
        sut.saveMovies(defaultMovieList)

        assertEquals(DEFAULT_SIZE_LIST, sut.getItemsCount())
    }

    @Test
    fun configureCell_DisplaysImage() {
        sut.saveMovies(defaultMovieList)

        sut.configureCell(cellView, 1)

        verify(cellView).displayImage(textCaptor.capture())
        assertEquals(URL_TO_DISPLAY, textCaptor.firstValue)
    }

    @Test
    fun onItemClick_SavesSelectedMovieId() {
        val fakePosition = 0
        val idExpected = defaultMovieList[fakePosition].id
        sut.saveMovies(defaultMovieList)

        sut.onItemClick(fakePosition)

        assertEquals(idExpected, sut.getSelectedMovieId())
    }

    @Test
    fun onItemClick_InvokesNavigateToDetailScreen() {
        val fakePosition = 0
        val idExpected = defaultMovieList[fakePosition].id
        sut.saveMovies(defaultMovieList)

        sut.onItemClick(fakePosition)

        verify(view).navigateToDetailScreen(intCaptor.capture())
        assertEquals(idExpected.toLong(), intCaptor.firstValue.toLong())
    }


    private fun setMoviesAvailable(movieList: List<Movie>) {
        verify(useCase).execute(moviesHandlerCaptor.capture(), any())
        moviesHandlerCaptor.firstValue.handle(movieList)
    }

    private fun setMoviesError() {
        verify(useCase).execute(moviesHandlerCaptor.capture(), any())
        moviesHandlerCaptor.firstValue.error()
    }

}