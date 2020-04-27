package com.versilistyson.searchflix.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movieId") val movieId: Long,
    @ColumnInfo(name = "media_id") val mediaId: Long
)