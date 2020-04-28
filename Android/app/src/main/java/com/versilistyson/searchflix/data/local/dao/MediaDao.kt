package com.versilistyson.searchflix.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.versilistyson.searchflix.data.local.model.MediaData
import com.versilistyson.searchflix.domain.entities.Media

@Dao
interface MediaDao {
    @Query("SELECT * from media_table WHERE title LIKE :likeTitle")
    suspend fun getMediaByLikeTitle(likeTitle: String): List<MediaData>

    @Query("SELECT * from media_table WHERE title =:exactTitle")
    suspend fun getMovieByExactTitle(exactTitle: String): List<MediaData>

    @Update
    suspend fun updateMedia(vararg media: Media): Int

    @Delete
    suspend fun deleteMedia(vararg media: Media): Int

    @Query("SELECT * FROM media_table WHERE is_favorite IS 1")
    suspend fun getFavoriteMedia(): List<MediaData>
}