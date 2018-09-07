package com.joseangelmaneiro.movies.data.exception


class DatabaseException(errorMessage: String = "A database error has occurred"):
        Exception(errorMessage)