package com.versilistyson.searchflix.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.versilistyson.searchflix.data.remote.inteceptors.AuthorizationInterceptor
import com.versilistyson.searchflix.data.util.NetworkConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkingModule {


    @JvmStatic @Singleton
    @Provides
    fun provideHttpLoggingInteceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }

    @JvmStatic
    @Singleton @Provides
    fun provideOkHttpClientBuilder(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

    @JvmStatic
    @Singleton @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @JvmStatic
    @Singleton @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @JvmStatic
    @Singleton @Provides
    fun provideRetrofitBuilder(
        moshiConverterFactory: MoshiConverterFactory
    ) : Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
}