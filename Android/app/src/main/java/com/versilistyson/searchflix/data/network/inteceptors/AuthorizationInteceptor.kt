package com.versilistyson.searchflix.data.network.inteceptors

import com.versilistyson.searchflix.R
import okhttp3.Interceptor
import okhttp3.Response

object AuthorizationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${R.string.movie_api_key}")
                .build()

        return chain.proceed(newRequest)
    }
}