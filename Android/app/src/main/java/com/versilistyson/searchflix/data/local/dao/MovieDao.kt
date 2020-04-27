package com.versilistyson.searchflix.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.versilistyson.searchflix.data.local.model.MediaAndMovieData

@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * FROM media_table WHERE media_id =:mediaId")
    suspend fun getMediaAndMovieDataById(mediaId: Long): MediaAndMovieData

    @Transaction
    @Query("SELECT * FROM media_table WHERE is_favorite IS 1")
    suspend fun getFavorites(): List<MediaAndMovieData>
}