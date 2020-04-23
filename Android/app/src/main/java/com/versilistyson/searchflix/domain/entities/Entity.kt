package com.versilistyson.searchflix.domain.entities

import androidx.lifecycle.MutableLiveData
import java.io.Serializable

// TODO: Change to Parcelable?
sealed class Entity : Serializable

enum class MediaType : Serializable {
    TV,
    MOVIE
}

sealed class Media(
    val id: Int,
    val name: String,
    val summary: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val type: MediaType,
    val voteAverage: Double,
    val voteCount: Int
) : Entity() {

    data class Movie(
        val movieId: Int,
        val title: String = "",
        val overview: String = "",
        val movieReleaseDate: String,
        val moviePosterPath: String = "",
        val movieBackdropPath: String = "",
        val popularity: Double = 0.0,
        val movieVoteAverage: Double = 0.0,
        val movieVoteCount: Int = 0
    ) : Media(
        movieId,
        title,
        overview,
        movieReleaseDate,
        moviePosterPath,
        movieBackdropPath,
        MediaType.MOVIE,
        movieVoteAverage,
        movieVoteCount
    )

    data class Show(
        val showId: Int = 0,
        val title: String = "",
        val overview: String = "",
        val showReleaseDate: String,
        val showPosterPath: String = "",
        val showBackdropPath: String = "",
        val showVoteAverage: Double = 0.0,
        val showVoteCount: Int = 0
    ) : Media(
        showId,
        title,
        overview,
        showReleaseDate,
        showPosterPath,
        showBackdropPath,
        MediaType.TV,
        showVoteAverage,
        showVoteCount
    )
}

data class StreamLookupResponse(
    val streamLocationsResult: StreamLocationsResult // collection
) : Entity()
data class StreamLocationsResult(
    val streamLocations: List<StreamingLocation> // locations
) : Entity()
data class StreamingLocation(
    val iconPath: String = "", // icon
    val displayName: String = "", // display_name
    val pathToMedia: String = "" // url
) : Entity()
sealed class MediaSingleResponse(
    val results: List<Media>
) : Entity() {

    data class MovieSingleResponse(
        val movieResults: List<Media.Movie> = emptyList()
    ) : MediaSingleResponse(movieResults)
}

sealed class MediaPagedResponse(
    val page: Int,
    val totalNumOfResults: Int,
    val totalNumOfPages: Int,
    val results: List<Media>
) : Entity() {
    data class MoviePagedResponse(
        val currentPage: Int = 0,
        val totalResults: Int = 0,
        val totalPages: Int = 0,
        val movieResults: List<Media.Movie> = emptyList()
    ) : MediaPagedResponse(currentPage, totalResults, totalPages, movieResults)
}

data class Category(
    val title: String,
    val liveDataMediaList: MutableLiveData<List<Media>> = MutableLiveData(),
    var fetcherFn: (() -> Unit)? = null
) : Entity() {
    fun updateMediaList(mediaList: List<Media>) {
        liveDataMediaList.postValue(mediaList)
    }

    fun fetchMedia() = fetcherFn?.let { fn ->
        fn()
    }
}

