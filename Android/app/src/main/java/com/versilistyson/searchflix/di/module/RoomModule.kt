package com.versilistyson.searchflix.di.module

import android.content.Context
import androidx.room.Room
import com.versilistyson.searchflix.data.local.SearchFlixDatabase
import com.versilistyson.searchflix.data.local.dao.MediaDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    private const val DATABASE_NAME = "searchflix-db"

    @JvmStatic
    @Singleton
    @Provides
    fun provideSearchFlixDatabase(context: Context): SearchFlixDatabase =
        Room.databaseBuilder(context, SearchFlixDatabase::class.java, DATABASE_NAME).build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideMediaDao(searchFlixDatabase: SearchFlixDatabase): MediaDao =
        searchFlixDatabase.mediaDao()
}