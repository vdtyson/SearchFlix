package com.versilistyson.searchflix.data.util

import com.versilistyson.searchflix.data.remote.dto.Dto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure

fun <T: Dto<E>, E> handleNetworkResult(networkResult: NetworkResult<T>, default: E): Either<Failure, E> =
    when(networkResult) {
        is NetworkResult.Empty -> Either.Right(default)
        is NetworkResult.Data -> Either.Right(networkResult.value.mapToEntity())
    }

fun <T> handleFailure(failure: Failure): Either<Failure, T> =
    Either.Left(failure)