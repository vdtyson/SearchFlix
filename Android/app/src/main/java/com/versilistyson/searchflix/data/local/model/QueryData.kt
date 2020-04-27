package com.versilistyson.searchflix.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "query_table")
data class QueryData(
    @PrimaryKey
    @ColumnInfo(name = "query") val query: String
)