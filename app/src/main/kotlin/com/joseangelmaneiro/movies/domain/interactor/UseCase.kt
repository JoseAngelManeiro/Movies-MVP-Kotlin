package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Handler


interface UseCase<T, P> {

    fun execute(handler: Handler<T>, params: P)

}