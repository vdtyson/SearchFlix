package com.versilistyson.searchflix.data.util

import com.versilistyson.searchflix.data.network.dto.Dto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import retrofit2.Response

object NetworkResponse {
    fun <T> getResult(response: Response<T>): Either<Failure, NetworkResult<T>> {
        return when {

            response.isSuccessful -> {
                val data = response.body() ?: return Either.Right(NetworkResult.Empty())
                Either.Right(NetworkResult.Data(data))
            }

            else -> {
                Either.Left(
                    Failure.ServerError(response.code(), response.message() ?: "Unknown Error")
                )
            }
        }
    }
}