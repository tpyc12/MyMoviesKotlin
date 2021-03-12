package com.example.mymovieskotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymovieskotlin.pojo.FavouriteMovieInfo
import com.example.mymovieskotlin.pojo.MovieInfo

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieInfo>>

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouritesMovies(): LiveData<List<FavouriteMovieInfo>>

    @Query("SELECT * FROM movies WHERE id ==:movieId ")
    fun getMovieById(movieId: Int): LiveData<MovieInfo>

    @Query("SELECT * FROM favourite_movies WHERE id ==:movieId ")
    fun getFavouriteMovieById(movieId: Int): LiveData<FavouriteMovieInfo>

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesList(moviesList: List<MovieInfo>)

    @Insert
    fun insertMovie(movie: MovieInfo)

    @Delete
    fun deleteMovie(movie: MovieInfo)

    @Insert
    fun insertFavouriteMovie(movie: MovieInfo?)

    @Delete
    fun deleteFavouriteMovie(movie: FavouriteMovieInfo?)
}