package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.entity.mapper.EntityDataMapper
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.domain.Handler
import com.joseangelmaneiro.movies.domain.Movie
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


const val MOVIE_ID = 1234

class MoviesRepositoryImplTest {

    private val movieEntityList = TestUtils.createDefaultMovieEntityList()
    private val movieEntity = TestUtils.createMovieEntity()
    private val movieList = TestUtils.createDefaultMovieList()
    private val movie = TestUtils.createMovie()

    private lateinit var sut: MoviesRepositoryImpl

    @Mock
    private lateinit var localDataSource: MoviesLocalDataSource
    @Mock
    private lateinit var remoteDataSource: MoviesRemoteDataSource
    @Mock
    private lateinit var moviesHandler: Handler<List<Movie>>
    @Mock
    private lateinit var movieHandler: Handler<Movie>

    private val moviesHandlerCaptor = argumentCaptor<Handler<List<MovieEntity>>>()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = MoviesRepositoryImpl.getInstance(localDataSource, remoteDataSource, EntityDataMapper())
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
        setMoviesAvailable(movieEntityList)

        // First verify that all movies are deleted from local data source
        verify(localDataSource).deleteAllMovies()

        // Verify that the data fetched from the remote data source was saved in local
        verify(localDataSource).saveMovies(movieEntityList)

        // Verify the movies from the remote data source are returned
        verify(moviesHandler).handle(movieList)
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
        // Make the local data source return data
        givenAValidMovie(movieEntity)

        // When calling getMovie in the repository
        sut.getMovie(MOVIE_ID, movieHandler)

        // Verify the movie from the local data source are returned
        verify(movieHandler).handle(movie)
    }


    private fun setMoviesError() {
        verify(remoteDataSource).getMovies(moviesHandlerCaptor.capture())
        moviesHandlerCaptor.firstValue.error()
    }

    private fun setMoviesAvailable(movieEntityList: List<MovieEntity>) {
        verify(remoteDataSource).getMovies(moviesHandlerCaptor.capture())
        moviesHandlerCaptor.firstValue.handle(movieEntityList)
    }

    private fun givenAValidMovie(movieEntity: MovieEntity) {
        whenever(localDataSource.getMovie(eq(MOVIE_ID))).thenReturn(movieEntity)
    }

}