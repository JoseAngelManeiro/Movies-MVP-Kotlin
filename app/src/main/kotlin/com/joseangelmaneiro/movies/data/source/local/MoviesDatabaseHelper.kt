package com.joseangelmaneiro.movies.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.joseangelmaneiro.movies.data.Movie


// Database Info
private const val DATABASE_NAME = "moviesDB"
private const val DATABASE_VERSION = 1

// Table Name
private const val TABLE_MOVIE = "movie"

// Movie Table Columns
private const val KEY_ID = "id"
private const val KEY_VOTE_COUNT = "voteCount"
private const val KEY_VIDEO = "video"
private const val KEY_VOTE_AVERAGE = "voteAverage"
private const val KEY_TITLE = "title"
private const val KEY_POPULARITY = "popularity"
private const val KEY_POSTERPATH = "posterpath"
private const val KEY_ORIGINAL_LANGUAGE = "originalLanguage"
private const val KEY_ORIGINAL_TITLE = "originalTitle"
private const val KEY_GENRE_IDS = "genreIds"
private const val KEY_BACKDROPPATH = "backdroppath"
private const val KEY_ADULT = "adult"
private const val KEY_OVERVIEW = "overview"
private const val KEY_RELEASEDATE = "releasedate"


class MoviesDatabaseHelper private constructor(context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private var INSTANCE: MoviesDatabaseHelper? = null

        fun getInstance(context: Context): MoviesDatabaseHelper {
            if (INSTANCE == null) {
                INSTANCE = MoviesDatabaseHelper(context)
            }
            return INSTANCE!!
        }
    }


    // Called when the database is created for the FIRST time.
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_VOTE_COUNT + " INTEGER," +
                KEY_VIDEO + " INTEGER," +
                KEY_VOTE_AVERAGE + " TEXT," +
                KEY_TITLE + " TEXT," +
                KEY_POPULARITY + " REAL," +
                KEY_POSTERPATH + " TEXT," +
                KEY_ORIGINAL_LANGUAGE + " TEXT," +
                KEY_ORIGINAL_TITLE + " TEXT," +
                KEY_GENRE_IDS + " TEXT," +
                KEY_BACKDROPPATH + " TEXT," +
                KEY_ADULT + " INTEGER, " +
                KEY_OVERVIEW + " TEXT," +
                KEY_RELEASEDATE + " TEXT" +
                ")"

        db.execSQL(CREATE_MOVIE_TABLE)
    }

    // Called when the database needs to be upgraded.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Simplest implementation is to drop all old tables and recreate them
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
            onCreate(db)
        }
    }

    fun addMovies(movieList: List<Movie>) {
        val db = writableDatabase

        // It's a good idea to wrap our insert in a transaction.
        // This helps with performance and ensures consistency of the database.
        db.beginTransaction()
        try {
            for (movie in movieList) {
                val values = ContentValues()
                values.put(KEY_ID, movie.id)
                values.put(KEY_VOTE_COUNT, movie.voteCount)
                values.put(KEY_VIDEO, if (movie.video) 1 else 0)
                values.put(KEY_VOTE_AVERAGE, movie.voteAverage)
                values.put(KEY_TITLE, movie.title)
                values.put(KEY_POPULARITY, movie.popularity)
                values.put(KEY_POSTERPATH, movie.posterPath)
                values.put(KEY_ORIGINAL_LANGUAGE, movie.originalLanguage)
                values.put(KEY_ORIGINAL_TITLE, movie.originalTitle)
                values.put(KEY_GENRE_IDS, DBUtils.transformIntegerListToString(movie.genreIds))
                values.put(KEY_BACKDROPPATH, movie.backdropPath)
                values.put(KEY_ADULT, if (movie.adult) 1 else 0)
                values.put(KEY_OVERVIEW, movie.overview)
                values.put(KEY_RELEASEDATE, movie.releaseDate)

                db.insertOrThrow(TABLE_MOVIE, null, values)
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.d("MoviesDB", "Error while trying to add movie to database")
        } finally {
            db.endTransaction()
        }
    }

    fun getAllMovies(): List<Movie> {
        val movieList = mutableListOf<Movie>()

        val MOVIES_SELECT_QUERY = "SELECT * FROM $TABLE_MOVIE"

        val db = readableDatabase
        val cursor = db.rawQuery(MOVIES_SELECT_QUERY, null)
        try {
            if (cursor!!.moveToFirst()) {
                do {
                    movieList.add(createMovie(cursor))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d("MoviesDB", "Error while trying to get movies from database")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return movieList
    }

    fun getMovie(id: Int): Movie? {
        var movie: Movie? = null

        val MOVIE_SELECT_QUERY = "SELECT * FROM $TABLE_MOVIE WHERE $KEY_ID = $id"

        val db = readableDatabase
        val cursor = db.rawQuery(MOVIE_SELECT_QUERY, null)
        try {
            if (cursor!!.moveToFirst()) {
                do {
                    movie = createMovie(cursor)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d("MoviesDB", "Error while trying to get movie from database")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return movie
    }

    fun deleteAllMovies() {
        val db = writableDatabase
        try {
            db.delete(TABLE_MOVIE, null, null)
        } catch (e: Exception) {
            Log.d("MoviesDB", "Error while trying to delete all movies")
        }

    }

    private fun createMovie(cursor: Cursor): Movie {
        return Movie(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getInt(cursor.getColumnIndex(KEY_VOTE_COUNT)),
                cursor.getInt(cursor.getColumnIndex(KEY_VIDEO)) == 1,
                cursor.getString(cursor.getColumnIndex(KEY_VOTE_AVERAGE)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                cursor.getFloat(cursor.getColumnIndex(KEY_POPULARITY)),
                cursor.getString(cursor.getColumnIndex(KEY_POSTERPATH)),
                cursor.getString(cursor.getColumnIndex(KEY_ORIGINAL_LANGUAGE)),
                cursor.getString(cursor.getColumnIndex(KEY_ORIGINAL_TITLE)),
                DBUtils.transformStringToIntegerList(
                cursor.getString(cursor.getColumnIndex(KEY_GENRE_IDS))),
                cursor.getString(cursor.getColumnIndex(KEY_BACKDROPPATH)),
                cursor.getInt(cursor.getColumnIndex(KEY_ADULT)) == 1,
                cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW)),
                cursor.getString(cursor.getColumnIndex(KEY_RELEASEDATE)))
    }

}