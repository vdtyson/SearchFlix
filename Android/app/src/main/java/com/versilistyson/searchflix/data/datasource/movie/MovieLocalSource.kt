package com.versilistyson.searchflix.data.datasource.movie

import com.versilistyson.searchflix.data.local.dao.MediaDao
import com.versilistyson.searchflix.data.local.model.MediaData
import com.versilistyson.searchflix.domain.entities.MediaType
import javax.inject.Inject

class MovieLocalSource
@Inject constructor(private val mediaDao: MediaDao) {

    fun getMovieFavoritesFlow() =
        mediaDao.getFavoriteMediaByType(MediaType.MOVIE.name)

    suspend fun insert(vararg mediaData: MediaData) {
        mediaDao.insert(*mediaData)
    }

    fun getIsFavoriteByMediaId(id: Long) =
        mediaDao.getIsFavoriteByMediaId(id)
}