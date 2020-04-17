package com.versilistyson.searchflix.data.network.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.domain.entities.Media.Movie

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
    val posterPath: String,
    @Json(name = "popularity")
    val popularity: Int,
    @Json(name = "vote_average")
    val voteAverage: Int
) : Dto<Movie>() {
    override val entityMapper: Mappable<Movie>
        get() = object : Mappable<Movie> {
            override fun map(): Movie =
                Movie(
                    movieId = id,
                    title = title,
                    releaseDate = releaseDate,
                    overview = overview,
                    posterPath = posterPath,
                    popularity = popularity,
                    voteAverage = voteAverage
                )
        }
}