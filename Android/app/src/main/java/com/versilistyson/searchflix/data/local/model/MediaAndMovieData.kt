package com.versilistyson.searchflix.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class MediaAndMovieData(
    @Embedded val mediaData: MediaData,
    @Relation(
        parentColumn = "id",
        entityColumn = "media_id"
    )
    val movieData: MovieData
)