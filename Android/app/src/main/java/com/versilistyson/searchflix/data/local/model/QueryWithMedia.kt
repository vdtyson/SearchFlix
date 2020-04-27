package com.versilistyson.searchflix.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class QueryWithMedia(
    @Embedded val queryData: QueryData,
    @Relation(
        parentColumn = "query",
        entityColumn = "mediaId",
        associateBy = Junction(QueryMediaCrossRef::class)
    )
    val media: List<MediaData>
)