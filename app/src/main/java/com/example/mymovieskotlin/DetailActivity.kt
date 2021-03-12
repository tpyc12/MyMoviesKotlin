package com.example.mymovieskotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieskotlin.api.ApiService.Companion.BIG_POSTER_SIZE
import com.example.mymovieskotlin.databinding.ActivityDetailBinding
import com.example.mymovieskotlin.pojo.MovieInfo
import com.example.mymovieskotlin.viewModel.MovieViewModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel

    private lateinit var ui: ActivityDetailBinding

    private var id: Int? = null

    var movie: MovieInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ui = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(ui.root)
        if (!intent.hasExtra(EXTRA_ID)) {
            finish()
            return
        }
        id = intent.getIntExtra(EXTRA_ID, -1)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        id?.let {
            viewModel.getMovieById(it).observe(this, {
                ui.tvTitle.text = it.title
                ui.tvOriginalTitle.text = it.originalTitle
                ui.tvRating.text = it.voteAverage.toString()
                ui.tvOverview.text = it.overview
                ui.tvReleaseDate.text = it.releaseDate
                Picasso.get().load(it.getFullImageUrl(BIG_POSTER_SIZE)).into(ui.ivBigPoster)
            })

        }
    }

    companion object {
        const val EXTRA_ID = "id"

        fun newIntent(context: Context, id: Int?): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }

    fun onClickChangeFavourite(view: View) {
        val favouriteMovieInfo = id?.let { viewModel.getFavouriteMovieById(it) }
        movie = id?.let { viewModel.getMovieById(it).value }
        if (favouriteMovieInfo == null) {
            viewModel.insertFavouriteMovie(movie)
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovieInfo.value)
        }
//        val movie =  viewModel.getMovieById(id)
//        Log.d("TAGGGG", "$favouriteMovieInfo $movie")
//        if (favouriteMovieInfo == null) {
//            viewModel.insertFavouriteMovie(movie as FavouriteMovieInfo)
//        } else {
//            viewModel.deleteFavouriteMovie(favouriteMovieInfo as FavouriteMovieInfo)
//        }
    }

}