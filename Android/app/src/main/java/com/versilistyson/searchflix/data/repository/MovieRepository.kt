package com.versilistyson.searchflix.data.repository

import com.versilistyson.searchflix.data.datasource.search.MovieRemoteSource
import com.versilistyson.searchflix.data.remote.dto.Dto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.entities.MediaPagedResponse.MoviePagedResponse
import com.versilistyson.searchflix.domain.entities.MediaSingleResponse.MovieSingleResponse
import javax.inject.Inject

class MovieRepository
@Inject constructor(private val movieRemoteSource: MovieRemoteSource) {

    suspend fun getPopularMovies(
        language: String = "en-US",
        page: Int = 1
    ): Either<Failure, MoviePagedResponse> {
        val response = movieRemoteSource.fetchPopularMovies(language, page)
        return response.foldAndGet(::handleFailure) { networkResult ->
            handleNetworkResult(networkResult, MoviePagedResponse())
        }
    }

    suspend fun getTopRatedMovies(
        language: String = "en-US",
        page: Int = 1
    ): Either<Failure, MoviePagedResponse> {
        val response = movieRemoteSource.fetchTopRatedMovies(language, page)
        return response.foldAndGet(::handleFailure) { networkResult ->
            handleNetworkResult(networkResult, MoviePagedResponse())
        }
    }

    suspend fun getUpcomingMovies(
        language: String = "en-US"
    ): Either<Failure, MovieSingleResponse> {
        val response = movieRemoteSource.fetchUpcomingMovies(language)
        return response.foldAndGet(::handleFailure) { networkResult ->
            handleNetworkResult(networkResult, MovieSingleResponse())
        }
    }

    private suspend fun queryMovies(
        query: String,
        page: Int,
        language: String,
        isAdultIncluded: Boolean
    ): Either<Failure, MoviePagedResponse> {
        TODO()
    }

    private fun <T: Dto<E>, E> handleNetworkResult(networkResult: NetworkResult<T>, default: E): Either<Failure, E> =
        when(networkResult) {
            is NetworkResult.Empty -> Either.Right(default)
            is NetworkResult.Data -> Either.Right(networkResult.value.mapToEntity())
        }

    private fun <T> handleFailure(failure: Failure): Either<Failure, T> =
        Either.Left(failure)
}