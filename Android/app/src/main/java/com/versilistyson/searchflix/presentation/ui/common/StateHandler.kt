package com.versilistyson.searchflix.presentation.ui.common

import com.versilistyson.searchflix.domain.exception.Failure

interface StateHandler<S: State, T> {
    fun updateState(newState: S)
    fun handleFailure(failure: Failure)
    fun handleResult(result: T)
}