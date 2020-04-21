package com.versilistyson.searchflix.data.remote.inteceptors

import android.content.Context
import com.versilistyson.searchflix.data.util.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor
@Inject constructor(
    private val context: Context,
    private val internetConnectionListener: InternetConnectionListener
): Interceptor {

    private fun isConnected() = context.isNetworkAvailable()

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        if(!isConnected()) internetConnectionListener.onInternetUnavailable()
        return chain.proceed(request)
    }
}