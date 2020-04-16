package com.versilistyson.searchflix.data.util

sealed class NetworkResult<T>(val isEmpty: Boolean) {
    class Empty<T>: NetworkResult<T>(true)
    data class Data<T>(val value: T): NetworkResult<T>(false)
}
