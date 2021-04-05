package com.example.mymovieskotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieskotlin.api.ApiService.Companion.BIG_POSTER_SIZE
import com.example.mymovieskotlin.databinding.ActivityDetailBinding
import com.example.mymovieskotlin.pojo.FavouriteMovieInfo
import com.example.mymovieskotlin.pojo.MovieInfo
import com.example.mymovieskotlin.viewModel.MovieViewModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel

    private lateinit var ui: ActivityDetailBinding

    var id: Int = -1

    private lateinit var movie: MovieInfo

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
        movie = viewModel.onGetMovieById(id)
            Log.i("TAG", movie.toString())
            Log.i("TAG", id.toString())
        ui.tvTitle.text = movie.title
        ui.tvOriginalTitle.text = movie.originalTitle
        ui.tvRating.text = movie.voteAverage.toString()
        ui.tvOverview.text = movie.overview
        ui.tvReleaseDate.text = movie.releaseDate
        Picasso.get().load(movie.getFullImageUrl(BIG_POSTER_SIZE)).into(ui.ivBigPoster)
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
        val favouriteMovieInfo = viewModel.onGetFavouriteMovieById(id)
        if (favouriteMovieInfo == null) {
            movie.let { viewModel.onInsertFavouriteMovie(it) }
            Toast.makeText(this, "Added to favourite", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.onDeleteFavouriteMovie(favouriteMovieInfo)
            Toast.makeText(this, "Remove from favourite", Toast.LENGTH_SHORT).show()
        }
    }
}
