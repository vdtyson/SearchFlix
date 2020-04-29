package com.versilistyson.searchflix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.versilistyson.searchflix.data.local.dao.MediaDao
import com.versilistyson.searchflix.data.local.model.MediaData

@Database(
    entities = [MediaData::class],
    version = 1,
    exportSchema = false
)
abstract class SearchFlixDatabase: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}