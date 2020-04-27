package com.versilistyson.searchflix.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["query", "mediaId"])
data class QueryMediaCrossRef(
   val query: String,
   val mediaId: Long
)