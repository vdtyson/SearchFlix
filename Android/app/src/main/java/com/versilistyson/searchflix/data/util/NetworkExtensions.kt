@file:Suppress("DEPRECATION")

package com.versilistyson.searchflix.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun Context.isNetworkAvailable(): Boolean {
    var result: Boolean = false
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    when {

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            connectivityManager?.run {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            result = true
                        }
                    }
                }
            }
        }

        else -> {
            connectivityManager?.run {
                connectivityManager.activeNetworkInfo?.run {
                    when {
                        type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE -> {
                            result = true
                        }
                    }
                }
            }
        }
    }

    return result
}