package com.example.mymovieskotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymovieskotlin.adapters.MovieAdapter
import com.example.mymovieskotlin.api.ApiService.Companion.SORTED_BY_POPULARITY
import com.example.mymovieskotlin.api.ApiService.Companion.SORTED_BY_TOP_RATED
import com.example.mymovieskotlin.databinding.ActivityMainBinding
import com.example.mymovieskotlin.viewModel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel

    private lateinit var ui: ActivityMainBinding

    private lateinit var methodOfSort: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        val adapter = MovieAdapter()
        ui.rvMovies.layoutManager = GridLayoutManager(this, 2)
        ui.rvMovies.adapter = adapter
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.movies.observe(this, {
            adapter.movieInfoList = it
        })
        ui.switchSort.isChecked = true
        ui.switchSort.setOnCheckedChangeListener { buttonView, isChecked ->
            setMethodOfSort(isChecked)
        }
        ui.switchSort.isChecked = false
        adapter.onPosterClickListener = object : MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val movie = adapter.movieInfoList[position]
                val intent = DetailActivity.newIntent(
                    this@MainActivity,
                    movie.id
                )
                startActivity(intent)
            }
        }
        adapter.onReachEndListener = object : MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                Toast.makeText(this@MainActivity, "The end", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setMethodOfSort(isTopRated: Boolean) {
        if (isTopRated) {
            methodOfSort = SORTED_BY_TOP_RATED
            ui.switchSort.isChecked = true
            ui.tvPopularity.setTextColor(resources.getColor(R.color.white))
            ui.tvTopRated.setTextColor(resources.getColor(R.color.teal_200))
        } else {
            methodOfSort = SORTED_BY_POPULARITY
            ui.switchSort.isChecked = false
            ui.tvPopularity.setTextColor(resources.getColor(R.color.teal_200))
            ui.tvTopRated.setTextColor(resources.getColor(R.color.white))
        }
        viewModel.loadData(methodOfSort, 1)
    }

    fun onClickSetPopularity(view: View) {
        setMethodOfSort(false)
        ui.switchSort.isChecked = false
    }

    fun onClickSetTopRated(view: View) {
        setMethodOfSort(true)
        ui.switchSort.isChecked = true
    }
}