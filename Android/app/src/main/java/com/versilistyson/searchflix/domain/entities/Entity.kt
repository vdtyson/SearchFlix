package com.versilistyson.searchflix.domain.entities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.versilistyson.searchflix.data.local.model.MediaData
import com.versilistyson.searchflix.data.local.model.Persistent
import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.presentation.dashboard.MediaListStateComponent
import java.io.Serializable

// TODO: Change to Parcelable?
sealed class Entity : Serializable
abstract class Persistable<Self : Persistable<Self, P>, P : Persistent> : Entity() {

    abstract fun toPersistent(): P

    private val mapper = object : Mappable<P> {
        override fun map(): P = toPersistent()
    }

    fun mapToPersistent(): P = mapper.map()
}

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
    val voteCount: Int,
    val isMediaFavorite: Boolean
) : Persistable<Media, MediaData>() {

    data class Movie(
        val movieId: Int = -1,
        val title: String = "",
        val overview: String = "",
        val movieReleaseDate: String = "",
        val moviePosterPath: String = "",
        val movieBackdropPath: String = "",
        val popularity: Double = 0.0,
        val movieVoteAverage: Double = 0.0,
        val movieVoteCount: Int = 0,
        val isFavorite: Boolean = false
    ) : Media(
        movieId,
        title,
        overview,
        movieReleaseDate,
        moviePosterPath,
        movieBackdropPath,
        MediaType.MOVIE,
        movieVoteAverage,
        movieVoteCount,
        isFavorite
    )

    data class Show(
        val showId: Int = 0,
        val title: String = "",
        val overview: String = "",
        val showReleaseDate: String,
        val showPosterPath: String = "",
        val showBackdropPath: String = "",
        val showVoteAverage: Double = 0.0,
        val showVoteCount: Int = 0,
        val isFavorite: Boolean = false
    ) : Media(
        showId,
        title,
        overview,
        showReleaseDate,
        showPosterPath,
        showBackdropPath,
        MediaType.TV,
        showVoteAverage,
        showVoteCount,
        isFavorite
    )

    override fun toPersistent(): MediaData =
        MediaData(
            mediaId = id.toLong(),
            title = name,
            overview = summary,
            releaseDate = releaseDate,
            posterPath = posterPath,
            backdropPath = backdropPath,
            voteAverage = voteAverage,
            voteCount = voteCount.toLong(),
            isFavorite = isMediaFavorite,
            type = type.name
        )
}

data class StreamLookupResponse(
    val streamLocationsResult: StreamLocationsResult = StreamLocationsResult() // collection
) : Entity()

data class StreamLocationsResult(
    val streamLocations: List<StreamingLocation> = emptyList()// locations
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
    val mediaListState: LiveData<MediaListStateComponent>,
    var fetcherFn: (() -> Unit)? = null
) : Entity() {
    fun fetchMedia() = fetcherFn?.let { fn ->
        fn()
    }
}

// ISO-639-1 code
sealed class Language(val name: String, val code: String, val regions: Set<Region>) {
    object ENGLISH : Language(
        "English",
        "en",
        setOf(
            Region.UNITED_STATES,
            Region.UNITED_KINGDOM
        )
    )

    object SPANISH : Language(
        "Spanish",
        "es",
        setOf(
            Region.MEXICO,
            Region.SPAIN,
            Region.COLOMBIA,
            Region.ARGENTINA,
            Region.UNITED_STATES
        )
    ) {
    }
}

enum class Region(val regionName: String, val code: String) {
    UNITED_STATES("United States", "US"),
    UNITED_KINGDOM("United Kingdom", "GB"),
    MEXICO("Mexico", "MX"),
    SPAIN("Spain", "ES"),
    COLOMBIA("Colombia", "CO"),
    ARGENTINA("Argentina", "AR")
}

enum class Theme(val value: Int) {
    DAY(0),
    NIGHT(1)
}

