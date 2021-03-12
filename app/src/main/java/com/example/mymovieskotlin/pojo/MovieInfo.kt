package com.example.mymovieskotlin.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymovieskotlin.api.ApiFactory.BASE_IMAGE_URL
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieInfo (
    @SerializedName("adult")
    @Expose
    val adult: Boolean? = null,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("popularity")
    @Expose
    val popularity: Double? = null,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("video")
    @Expose
    val video: Boolean? = null,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null
){
    fun getFullImageUrl(posterSize: String): String {
        return BASE_IMAGE_URL + posterSize + posterPath
    }
}