package com.versilistyson.searchflix.domain.entities

import androidx.lifecycle.MutableLiveData

sealed class Entity

enum class MediaType {
    TV,
    MOVIE
}

sealed class Media(
    val id: Int,
    val name: String,
    val summary: String,
    val posterPath: String,
    val backdropPath: String,
    val type: MediaType
) : Entity() {
    data class Movie(
        val movieId: Int,
        val title: String = "",
        val releaseDate: String = "",
        val overview: String = "",
        val moviePosterPath: String = "",
        val movieBackdropPath: String = "",
        val popularity: Float = 50f,
        val voteAverage: Float = 5f
    ) : Media(movieId, title, overview, moviePosterPath, movieBackdropPath, MediaType.MOVIE)

    data class Show(
        val showId: Int = 0,
        val title: String = "",
        val overview: String = "",
        val showPosterPath: String = "",
        val showBackdropPath: String = ""
    ) : Media(showId, title, overview, showPosterPath, showBackdropPath, MediaType.TV)
}

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

