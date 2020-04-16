package com.versilistyson.searchflix.domain

sealed class NetworkSuccess<in T>(val isEmpty: Boolean) {
    class Empty<T>: NetworkSuccess<T>(true)
    data class Data<T>(val value: T): NetworkSuccess<T>(false)
}
