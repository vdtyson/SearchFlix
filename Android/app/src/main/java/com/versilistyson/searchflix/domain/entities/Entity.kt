package com.versilistyson.searchflix.domain.entities

import androidx.lifecycle.MutableLiveData

sealed class Entity

sealed class Media(val id: Int, val name: String = "", val summary: String = "", val imagePath: String = "") : Entity() {
    data class Movie(
        val movieId: Int,
        val title: String = "",
        val releaseDate: String = "",
        val overview: String = "",
        val posterPath: String = "",
        val popularity: Float = 50f,
        val voteAverage: Float = 5f
    ) : Media(movieId, title,overview, posterPath)

    data class Show(
        val showId: Int = 0,
        val title: String = "",
        val overview: String = "",
        val posterPath: String = ""
    ) : Media(showId,title, overview, posterPath)
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
   ) : MediaPagedResponse(currentPage,totalResults,totalPages,movieResults)
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

