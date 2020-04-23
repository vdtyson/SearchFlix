package com.versilistyson.searchflix.data.remote.inteceptors

import com.versilistyson.searchflix.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object TMDBAuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_KEY}")
                .build()

        return chain.proceed(newRequest)
    }
}