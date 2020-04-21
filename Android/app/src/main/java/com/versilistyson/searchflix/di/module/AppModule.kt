package com.versilistyson.searchflix.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    private const val SEARCH_FLIX_PREFS = "SEARCH_FLIX_PREFS"

    @Singleton
    @Provides @JvmStatic
    fun provideApplicationContext(application: Application): Context =
        application.applicationContext

    @Singleton
    @Provides @JvmStatic
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(SEARCH_FLIX_PREFS, Context.MODE_PRIVATE)
}