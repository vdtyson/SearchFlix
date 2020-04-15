package com.versilistyson.searchflix.domain.usecase

import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<out Type, in Params> {

    abstract suspend fun run(params: Params): Flow<Either<Failure, Type>>

    @ExperimentalCoroutinesApi
    suspend fun invoke(
        params: Params,
        dispatcher: CoroutineDispatcher
    ) : Flow<Either<Failure, Type>> = run(params).flowOn(dispatcher)
}