package com.versilistyson.searchflix.data.network.inteceptors

import android.content.Context
import com.versilistyson.searchflix.BuildConfig
import com.versilistyson.searchflix.R
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

object AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_KEY}")
                .build()

        return chain.proceed(newRequest)
    }
}