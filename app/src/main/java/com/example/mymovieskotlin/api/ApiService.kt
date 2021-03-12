package com.example.mymovieskotlin.api

import com.example.mymovieskotlin.pojo.MovieListOfResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("3/discover/movie")
    fun getMovieList(
        @Query(QUERY_PARAMS_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAMS_LANGUAGE) lang: String,
        @Query(QUERY_PARAMS_SORT_BY) sortBy: String,
        @Query(QUERY_PARAMS_MIN_VOTE_COUNT) minVoteCount: String = MIN_VOTE_COUNT,
        @Query(QUERY_PARAMS_PAGE) page: Int
    ): Single<MovieListOfResults>

    companion object {
        private const val QUERY_PARAMS_API_KEY = "api_key"
        private const val QUERY_PARAMS_LANGUAGE = "language"
        private const val QUERY_PARAMS_SORT_BY = "sort_by"
        private const val QUERY_PARAMS_PAGE = "page"
        private const val QUERY_PARAMS_MIN_VOTE_COUNT = "vote_count.gte"

        private const val API_KEY = "755e824a2eea83d4be103f6a5d396606"
        const val SORTED_BY_POPULARITY = "popularity.desc"
        const val SORTED_BY_TOP_RATED = "vote_average.desc"
        private const val MIN_VOTE_COUNT = "500"
        const val SMALL_POSTER_SIZE = "w185"
        const val BIG_POSTER_SIZE = "w780"
    }
}