package com.versilistyson.searchflix.domain

import java.lang.Exception

sealed class Failure {
    object NetworkConnection: Failure()
    data class ServerError(val statusCode: Int? = null, val errorMessage: String? = null): Failure()

    open class FeatureFailure(exception: Exception? = null) : Failure()

    override fun equals(other: Any?): Boolean {
        return other is Failure
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}