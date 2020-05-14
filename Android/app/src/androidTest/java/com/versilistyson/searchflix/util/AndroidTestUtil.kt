package com.versilistyson.searchflix.util

import com.versilistyson.searchflix.data.local.model.MediaData
import com.versilistyson.searchflix.domain.entities.MediaType

object AndroidTestUtil {
    fun createMediaData(id: Long = 1L, isFavorite: Boolean = false, mediaType: String = MediaType.MOVIE.name): MediaData =
        MediaData(
           mediaId = id,
            title = "title",
            overview = "",
            releaseDate = "releaseDate",
            posterPath = "posterPath",
            backdropPath = "backdropPath",
            voteAverage = 8.0,
            voteCount = 1L,
            isFavorite = isFavorite,
            type = mediaType
        )
}