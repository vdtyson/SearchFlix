package com.versilistyson.searchflix.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.versilistyson.searchflix.domain.exception.Failure

interface StateHandler<S: State<T>, T> {
    fun updateState(newState: S)
    fun handleFailure(failure: Failure)
    fun handleResult(result: T)
}