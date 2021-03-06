package com.versilistyson.searchflix.di.module

import com.versilistyson.searchflix.data.remote.api.MovieApi
import com.versilistyson.searchflix.data.remote.api.StreamLookupApi
import com.versilistyson.searchflix.data.remote.inteceptors.TMDBAuthorizationInterceptor
import com.versilistyson.searchflix.data.remote.inteceptors.UTellyAuthorizationInterceptor
import com.versilistyson.searchflix.data.util.NetworkConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ServiceModule {

    @Singleton @JvmStatic
    @Provides
    fun provideMovieApi(
        okHttpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ) : MovieApi {

        val okHttpClient =
            okHttpClientBuilder
                .addInterceptor(TMDBAuthorizationInterceptor)
                .build()

        val retrofit =
            retrofitBuilder
                .client(okHttpClient)
                .baseUrl(NetworkConstants.TMDB_V3_BASE_URL)
                .build()


        return retrofit.create(MovieApi::class.java)
    }

    @Singleton @JvmStatic
    @Provides
    fun provideStreamLookupApi(
        okHttpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ) : StreamLookupApi {

        val okHttpClient =
            okHttpClientBuilder
                .addInterceptor(UTellyAuthorizationInterceptor)
                .build()

        val retrofit =
            retrofitBuilder
                .client(okHttpClient)
                .baseUrl(NetworkConstants.UTELLY_BASE_URL)
                .build()

        return retrofit.create(StreamLookupApi::class.java)
    }

}