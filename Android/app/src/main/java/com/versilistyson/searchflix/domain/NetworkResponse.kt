package com.versilistyson.searchflix.domain

import com.versilistyson.searchflix.domain.NetworkSuccess.*
import retrofit2.Response

object NetworkResponse {
    fun <T> getResult(response: Response<T>): Either<Failure.ServerError, NetworkSuccess<T>> {
        when {

            response.isSuccessful -> {
                val data = response.body() ?: return Either.Right(Empty())
                return Either.Right(Data(data))
            }

            else -> {
                return Either.Left(
                    Failure.ServerError(response.code(), response.message() ?: "Unknown Error")
                )
            }
        }
    }
}