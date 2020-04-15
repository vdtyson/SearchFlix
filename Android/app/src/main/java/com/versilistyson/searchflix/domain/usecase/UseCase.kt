package com.versilistyson.searchflix.domain.usecase

import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type: Any? {
    abstract suspend fun run(params: Params): Either<Failure, Type>

    open fun invoke(
        scope: CoroutineScope,
        params: Params,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async(dispatcher) { run(params) }
        scope.launch {
            onResult(
                backgroundJob.await()
            )
        }
    }

    class NoParams
}