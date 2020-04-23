package com.versilistyson.searchflix.data.remote.inteceptors

import com.versilistyson.searchflix.BuildConfig
import com.versilistyson.searchflix.data.util.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response

object UTellyAuthorizationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request().newBuilder()
                .addHeader("x-rapid-host", NetworkConstants.RAPID_API_HOST)
                .addHeader("x-rapidapi-key", BuildConfig.RAPID_API_KEY)
                .build()

        return chain.proceed(newRequest)
    }
}