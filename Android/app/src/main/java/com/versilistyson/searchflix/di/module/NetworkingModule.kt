package com.versilistyson.searchflix.di.module

import com.versilistyson.searchflix.data.network.inteceptors.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkingModule {

    private val BASE_URL = "https://api.themoviedb.org/3"

    @JvmStatic
    @Singleton @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @JvmStatic
    @Singleton @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @JvmStatic
    @Singleton @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ) : Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(BASE_URL)
            .build()


}