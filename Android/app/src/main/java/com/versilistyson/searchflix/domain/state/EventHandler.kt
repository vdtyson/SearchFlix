package com.versilistyson.searchflix.domain.state

import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

open class EventHandler<T>(private val onEvent: (T) -> Unit) : FlowCollector<T> {
    override suspend fun emit(value: T) {
        onEvent(value)
    }
}