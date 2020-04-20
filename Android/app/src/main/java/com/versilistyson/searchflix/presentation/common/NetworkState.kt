package com.versilistyson.searchflix.presentation.common

import com.versilistyson.searchflix.domain.exception.Failure

sealed class NetworkState<T> : State<T> {
    class Loading<T>: NetworkState<T>()
    data class Loaded<T>(val data: T): NetworkState<T>()
    data class Error<T>(val failure: Failure): NetworkState<T>()
}