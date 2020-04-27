package com.versilistyson.searchflix.data.local.dao

import androidx.room.Query
import androidx.room.Transaction
import com.versilistyson.searchflix.data.local.model.QueryData
import com.versilistyson.searchflix.data.local.model.QueryWithMedia

interface QueryDao {

    @Transaction
    @Query("SELECT * FROM query_table WHERE `query` =:query")
    suspend fun getQueryWithMedia(query: String): List<QueryWithMedia>

    @Query("SELECT * FROM query_table WHERE `query` LIKE :string")
    suspend fun getLikeQueries(string: String): List<QueryData>
}