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

class GetMovieTest{

    private val fakeMovieId = 1234

    lateinit var sut: GetMovie
    @Mock
    lateinit var repository: MoviesRepository
    @Mock
    lateinit var handler: Handler<Movie>

    val movieCaptor = argumentCaptor<Handler<Movie>>()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = GetMovie(repository)
    }

    @Test
    fun execute_InvokesRepository() {
        sut.execute(handler, GetMovie.Params(fakeMovieId))

        verify(repository).getMovie(eq(fakeMovieId), any())
    }

    @Test
    fun execute_ReturnsMovie() {
        val movie = TestUtils.createMovie()

        sut.execute(handler, GetMovie.Params(fakeMovieId))
        setMovieAvailable(movie)

        verify(handler).handle(eq(movie))
    }


    private fun setMovieAvailable(movie: Movie) {
        verify(repository).getMovie(eq(fakeMovieId), movieCaptor.capture())
        movieCaptor.firstValue.handle(movie)
    }

}