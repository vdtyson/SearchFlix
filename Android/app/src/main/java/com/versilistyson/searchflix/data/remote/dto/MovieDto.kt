package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.domain.entities.Media.Movie
import java.text.SimpleDateFormat
import java.util.*

data class MovieDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "popularity")
    val popularity: Float,
    @Json(name = "vote_average")
    val voteAverage: Float,
    @Json(name = "video")
    val hasVideo: Boolean
) : Dto<Movie>() {
    override val entityMapper: Mappable<Movie>
        get() = object : Mappable<Movie> {
            override fun map(): Movie =
                Movie(
                    movieId = id,
                    title = title,
                    movieReleaseDate = releaseDate,
                    overview = overview,
                    moviePosterPath =
                    when (posterPath) {
                        null -> ""
                        else -> posterPath
                    },
                    movieBackdropPath =
                    when (backdropPath) {
                        null -> ""
                        else -> backdropPath
                    },
                    popularity = popularity,
                    voteAverage = voteAverage
                )
        }
}