package com.example.mymovieskotlin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.mymovieskotlin.api.ApiFactory
import com.example.mymovieskotlin.database.AppDatabase
import com.example.mymovieskotlin.pojo.FavouriteMovieInfo
import com.example.mymovieskotlin.pojo.MovieInfo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val movies = db.movieInfoDao().getAllMovies()
    val favouriteMoviesInfo = db.movieInfoDao().getAllFavouritesMovies()

    private var favouriteMovie: FavouriteMovieInfo? = null
    private lateinit var movie: MovieInfo


    fun onDeleteFavouriteMovie(favouriteMovieInfo: FavouriteMovieInfo) {
        uiScope.launch {
            deleteFavouriteMovie(favouriteMovieInfo)
        }
    }

    private suspend fun deleteFavouriteMovie(favouriteMovieInfo: FavouriteMovieInfo) {
        withContext(Dispatchers.IO) {
            db.movieInfoDao().deleteFavouriteMovie(favouriteMovieInfo)
        }
    }

    fun onInsertFavouriteMovie(favouriteMovieInfo: MovieInfo) {
        uiScope.launch {
            insertFavouriteMovie(favouriteMovieInfo)
        }
    }

    private suspend fun insertFavouriteMovie(favouriteMovieInfo: MovieInfo) {
        withContext(Dispatchers.IO) {
            db.movieInfoDao().insertFavouriteMovie(favouriteMovieInfo)
        }
    }

    fun onGetFavouriteMovieById(id: Int): FavouriteMovieInfo? {
        uiScope.launch {
            favouriteMovie = getFavouriteMovieById(id)
        }
        return favouriteMovie
    }

    private suspend fun getFavouriteMovieById(id: Int): FavouriteMovieInfo? {
        return withContext(Dispatchers.IO) {
            db.movieInfoDao().getFavouriteMovieById(id)
        }
    }

    fun onDeleteMovie(movieInfo: MovieInfo) {
        uiScope.launch {
            deleteMovie(movieInfo)
        }
    }

    private suspend fun deleteMovie(movieInfo: MovieInfo) {
        withContext(Dispatchers.IO) {
            db.movieInfoDao().deleteMovie(movieInfo)
        }
    }

    fun onInsertMovie(movieInfo: MovieInfo) {
        uiScope.launch {
            insertMovie(movieInfo)
        }
    }

    private suspend fun insertMovie(movieInfo: MovieInfo) {
        withContext(Dispatchers.IO) {
            db.movieInfoDao().insertMovie(movieInfo)
        }
    }

    fun onDeleteAllMovies() {
        uiScope.launch {
            deleteAllMovies()
        }
    }

    private suspend fun deleteAllMovies() {
        withContext(Dispatchers.IO) {
            db.movieInfoDao().deleteAllMovies()
        }
    }

    fun onGetMovieById(id: Int): MovieInfo {
        uiScope.async {
            movie = getMovieById(id)
        }
        return movie
    }

    private suspend fun getMovieById(id: Int): MovieInfo {
        return withContext(Dispatchers.IO) {
            db.movieInfoDao().getMovieById(id)
        }
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
        viewModelJob.cancel()
    }
}
