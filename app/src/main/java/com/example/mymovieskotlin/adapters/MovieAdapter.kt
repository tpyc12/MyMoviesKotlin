package com.example.mymovieskotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieskotlin.R
import com.example.mymovieskotlin.api.ApiService.Companion.SMALL_POSTER_SIZE
import com.example.mymovieskotlin.databinding.MovieItemBinding
import com.example.mymovieskotlin.pojo.MovieInfo
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movieInfoList: List<MovieInfo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onPosterClickListener: OnPosterClickListener? = null

    var onReachEndListener: OnReachEndListener? = null

    interface OnPosterClickListener{
        fun onPosterClick(position: Int)
    }

    interface OnReachEndListener{
        fun onReachEnd()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (position > movieInfoList.size - 4 && onReachEndListener != null){
            onReachEndListener?.onReachEnd()
        }
        val movie = movieInfoList[position]
        Picasso.get().load(movie.getFullImageUrl(SMALL_POSTER_SIZE)).into(holder.ivSmallPoster)
        holder.itemView.setOnClickListener {
            onPosterClickListener?.onPosterClick(position)
        }
    }

    override fun getItemCount() = movieInfoList.size


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ui: MovieItemBinding = MovieItemBinding.bind(itemView)

        val ivSmallPoster = ui.ivSmallPoster
    }

    fun addMovies(movies: ArrayList<MovieInfo>) {
        movies.addAll(movies)
        notifyDataSetChanged()
    }

}