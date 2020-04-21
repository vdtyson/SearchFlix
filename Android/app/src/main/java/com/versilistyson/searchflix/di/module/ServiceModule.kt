package com.versilistyson.searchflix.di.module

import com.versilistyson.searchflix.data.remote.api.MovieApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ServiceModule {

    @Singleton @JvmStatic
    @Provides
    fun provideMovieApi(retrofit: Retrofit) : MovieApi =
        retrofit.create(MovieApi::class.java)

}