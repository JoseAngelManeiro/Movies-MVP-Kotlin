package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class GetMoviesTest{

    lateinit var sut: GetMovies
    @Mock
    lateinit var repository: MoviesRepository
    @Mock
    lateinit var handler: Handler<List<Movie>>

    val moviesCaptor = argumentCaptor<Handler<List<Movie>>>()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = GetMovies(repository)
    }

    @Test
    fun execute_InvokesRepository() {
        sut.execute(handler, Unit)

        verify(repository).getMovies(any())
    }

    @Test
    fun execute_ReturnsMovies() {
        val movies = TestUtils.createDefaultMovieList()

        sut.execute(handler, Unit)
        setMoviesAvailable(movies)

        verify(handler).handle(eq(movies))
    }


    private fun setMoviesAvailable(movies: List<Movie>) {
        verify(repository).getMovies(moviesCaptor.capture())
        moviesCaptor.firstValue.handle(movies)
    }

}