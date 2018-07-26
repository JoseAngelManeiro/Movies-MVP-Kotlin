package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class MoviesRepositoryImplTest {

    private val movies = TestUtils.createMainMovieList()
    private val movie = TestUtils.createMainMovie()

    private lateinit var sut: MoviesRepositoryImpl

    @Mock
    private lateinit var localDataSource: MoviesLocalDataSource
    @Mock
    private lateinit var remoteDataSource: MoviesRemoteDataSource
    @Mock
    private lateinit var moviesHandler: Handler<List<Movie>>
    @Mock
    private lateinit var movieHandler: Handler<Movie>

    private val moviesHandlerCaptor = argumentCaptor<Handler<List<Movie>>>()
    private val movieHandlerCaptor = argumentCaptor<Handler<Movie>>()



    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = MoviesRepositoryImpl.getInstance(localDataSource, remoteDataSource)
    }

    @After
    fun destroyRepositoryInstance() {
        MoviesRepositoryImpl.destroyInstance()
    }

    @Test
    fun getMovies_ReturnsAllMoviesFromRemoteDataSource() {
        // When calling getMovies in the repository
        sut.getMovies(moviesHandler)

        // Make the remote data source return data
        setMoviesAvailable(movies)

        // First verify that all movies are deleted from local data source
        verify(localDataSource).deleteAllMovies()

        // Verify that the data fetched from the remote data source was saved in local
        verify(localDataSource).saveMovies(movies)

        // Verify the movies from the remote data source are returned
        verify(moviesHandler).handle(movies)
    }

    @Test
    fun getMovies_FiresErrorFromRemoteDataSource() {
        // When calling getMovies in the repository
        sut.getMovies(moviesHandler)

        // Make the remote data source return error
        setMoviesError()

        // Verify that the error is returned
        verify(moviesHandler).error()
    }

    @Test
    fun getMovie_ReturnsMovieFromLocalDataSource() {
        // When calling getMovie in the repository
        sut.getMovie(movie.id, movieHandler)

        // Make the local data source return data
        setMovieAvailable(movie)

        // Verify the movie from the local data source are returned
        verify(movieHandler).handle(movie)
    }

    @Test
    fun getMovie_FiresErrorFromLocalDataSource() {
        // When calling getMovie in the repository
        sut.getMovie(movie.id, movieHandler)

        // Make the local data source return error
        setMovieError(movie.id)

        // Verify that the error is returned
        verify(movieHandler).error()
    }


    private fun setMoviesError() {
        verify(remoteDataSource).getMovies(moviesHandlerCaptor.capture())
        moviesHandlerCaptor.firstValue.error()
    }

    private fun setMoviesAvailable(movieList: List<Movie>) {
        verify(remoteDataSource).getMovies(moviesHandlerCaptor.capture())
        moviesHandlerCaptor.firstValue.handle(movieList)
    }

    private fun setMovieError(movieId: Int) {
        verify(localDataSource).getMovie(eq(movieId), movieHandlerCaptor.capture())
        movieHandlerCaptor.firstValue.error()
    }

    private fun setMovieAvailable(movie: Movie) {
        verify(localDataSource).getMovie(eq(movie.id), movieHandlerCaptor.capture())
        movieHandlerCaptor.firstValue.handle(movie)
    }

}