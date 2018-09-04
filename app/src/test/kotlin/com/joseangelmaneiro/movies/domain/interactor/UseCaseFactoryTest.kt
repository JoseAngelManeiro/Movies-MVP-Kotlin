package com.joseangelmaneiro.movies.domain.interactor

import org.junit.Assert.*
import org.mockito.MockitoAnnotations
import org.junit.Before
import com.joseangelmaneiro.movies.domain.MoviesRepository
import org.junit.Test
import org.mockito.Mock



class UseCaseFactoryTest{

    @Mock
    lateinit var repository: MoviesRepository

    lateinit var sut: UseCaseFactory


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = UseCaseFactory(repository)
    }

    @Test
    fun getMovie_CreatesCorrectInstance() {
        val useCase = sut.getMovie()

        assertTrue(useCase is GetMovie)
    }

    @Test
    fun getMovies_CreatesCorrectInstance() {
        val useCase = sut.getMovies()

        assertTrue(useCase is GetMovies)
    }

}