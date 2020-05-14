package com.versilistyson.searchflix

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.versilistyson.searchflix.data.local.SearchFlixDatabase
import com.versilistyson.searchflix.data.local.dao.MediaDao
import com.versilistyson.searchflix.data.local.model.MediaData
import com.versilistyson.searchflix.util.AndroidTestUtil
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MediaDaoTest {
    lateinit var mediaDao: MediaDao
    private lateinit var testDb: SearchFlixDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testDb = Room.inMemoryDatabaseBuilder(context, SearchFlixDatabase::class.java).build()
        mediaDao = testDb.mediaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        testDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeMediaDataAndOnlyReadFavorites() {

        runBlocking {
            // GIVEN
            val mediaDataList = arrayOf(
                AndroidTestUtil.createMediaData(1, true),
                AndroidTestUtil.createMediaData(2, false),
                AndroidTestUtil.createMediaData(3, true),
                AndroidTestUtil.createMediaData(4, false),
                AndroidTestUtil.createMediaData(5, true),
                AndroidTestUtil.createMediaData(6, false)
            )
            val expected = listOf(mediaDataList[0], mediaDataList[2], mediaDataList[4])
            val favoriteMediaFlow: Flow<List<MediaData>>
            // WHEN
            mediaDao.insert(*mediaDataList)
            favoriteMediaFlow = mediaDao.getFavoriteMedia()
            // THEN
            favoriteMediaFlow.collect { actual ->
                assertEquals(expected, actual)
            }
        }

    }

    @Test
    fun getIsFavoriteByMediaIdShouldReturnTrue() {
        runBlocking {
            // GIVEN
            val expected = true
            // WHEN
            mediaDao.insert(AndroidTestUtil.createMediaData(1, true))
            val isFavoriteMediaFlow = mediaDao.getIsFavoriteByMediaId(1)
            // Then
            isFavoriteMediaFlow.collect { actual ->
                assertEquals(expected, actual)
            }
        }

    }

    @Test
    fun flowShouldShowFavoritesBeingUpdated() {
        runBlocking {
            // GIVEN
            val expected = listOf(null, false, true, false)
            val actual = mutableListOf<Boolean?>()
            // WHEN
            mediaDao.getIsFavoriteByMediaId(1).collect { value -> actual.add(value) }
            mediaDao.insert(AndroidTestUtil.createMediaData(1, false))
            mediaDao.insert(AndroidTestUtil.createMediaData(1, true))
            mediaDao.insert(AndroidTestUtil.createMediaData(1, false))
            // THEN
            assertEquals(expected, actual.toList())
        }
    }
}