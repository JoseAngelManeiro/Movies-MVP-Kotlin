package com.joseangelmaneiro.movies.domain


interface UseCase<T, P> {

    fun execute(handler: Handler<T>, params: P)

}