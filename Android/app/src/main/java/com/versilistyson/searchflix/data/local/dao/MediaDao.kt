package com.versilistyson.searchflix.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.versilistyson.searchflix.data.local.model.MediaData
import com.versilistyson.searchflix.domain.entities.Media
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao: BaseDao<MediaData> {

    @Query("SELECT * FROM media_table WHERE is_favorite IS 1")
    fun getFavoriteMedia(): Flow<List<MediaData>>

    @Query("SELECT * FROM media_table WHERE is_favorite IS 1 AND type =:mediaType")
    fun getFavoriteMediaByType(mediaType: String): Flow<List<MediaData>>
}