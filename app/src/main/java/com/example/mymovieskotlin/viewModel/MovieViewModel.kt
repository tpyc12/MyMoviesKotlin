package com.example.mymovieskotlin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mymovieskotlin.api.ApiFactory
import com.example.mymovieskotlin.database.AppDatabase
import com.example.mymovieskotlin.pojo.FavouriteMovieInfo
import com.example.mymovieskotlin.pojo.MovieInfo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val movies = db.movieInfoDao().getAllMovies()
    val favouriteMovieInfo = db.movieInfoDao().getAllFavouritesMovies()

    fun deleteFavouriteMovie(favouriteMovieInfo: FavouriteMovieInfo?) {
       db.movieInfoDao().deleteFavouriteMovie(favouriteMovieInfo)
    }

    fun insertFavouriteMovie(favouriteMovieInfo: MovieInfo?) {
        db.movieInfoDao().insertFavouriteMovie(favouriteMovieInfo)
    }

    fun getFavouriteMovieById(id: Int): LiveData<FavouriteMovieInfo> {
        return db.movieInfoDao().getFavouriteMovieById(id)
    }

    fun deleteMovie(movieInfo: MovieInfo) {
        db.movieInfoDao().deleteMovie(movieInfo)
    }

    fun insertMovie(movieInfo: MovieInfo) {
        db.movieInfoDao().insertMovie(movieInfo)
    }

    fun deleteAllMovies() {
        db.movieInfoDao().deleteAllMovies()
    }

    fun getMovieById(id: Int): LiveData<MovieInfo> {
        return db.movieInfoDao().getMovieById(id)
    }

    fun loadData(sort: String, page: Int) {
        val disposable =
            ApiFactory.apiService.getMovieList(lang = "en-US", page = page, sortBy = sort)
                .map { it.results }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    db.movieInfoDao().deleteAllMovies()
                    db.movieInfoDao().insertMoviesList(it)
                    Log.d("TAG", it.toString())
                }, {
                    Log.d("TAG", it.message.toString())
                })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
