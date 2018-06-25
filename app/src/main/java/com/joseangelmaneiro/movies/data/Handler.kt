package com.joseangelmaneiro.movies.data

//Callback to comunicate presenter with repository
interface Handler<T> {

    fun handle(result: T)

    fun error()

}