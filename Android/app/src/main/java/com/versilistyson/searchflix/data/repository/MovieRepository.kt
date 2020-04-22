package com.versilistyson.searchflix.data.repository

import com.versilistyson.searchflix.data.datasource.search.MovieRemoteSource
import com.versilistyson.searchflix.data.remote.dto.MoviePagedResponseDto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.entities.MediaPagedResponse.MoviePagedResponse
import javax.inject.Inject

class MovieRepository
@Inject constructor(private val movieRemoteSource: MovieRemoteSource) {

    suspend fun getPopularMovies(
        language: String = "en-US",
        page: Int = 1
    ): Either<Failure, MoviePagedResponse> {
        val response = movieRemoteSource.fetchPopularMovies(language, page)
        return foldAndGetPagedResponse(response)
    }

    suspend fun getTopRatedMovies(
        language: String = "en-US",
        page: Int = 1
    ): Either<Failure, MoviePagedResponse> {
        val response = movieRemoteSource.fetchTopRatedMovies(language, page)
        return foldAndGetPagedResponse(response)
    }

    private suspend fun queryMovies(
        query: String,
        page: Int,
        language: String,
        isAdultIncluded: Boolean
    ): Either<Failure, MoviePagedResponse> {
        val response = movieRemoteSource.searchMovies(query, page, language, isAdultIncluded)
        TODO()
    }

    private fun foldAndGetPagedResponse(response: Either<Failure, NetworkResult<MoviePagedResponseDto>>) =
        response.foldAndGet(::handleFailure, ::handlePagedResult)

    private fun handlePagedResult(networkResult: NetworkResult<MoviePagedResponseDto>): Either<Failure, MoviePagedResponse> =
        when (networkResult) {
            is NetworkResult.Empty -> Either.Right(MoviePagedResponse())
            is NetworkResult.Data -> Either.Right(networkResult.value.mapToEntity())
        }

    private fun handleResult() {
        TODO()
    }

    // TODO: Maybe add some feature failures
    private fun handleFailure(failure: Failure): Either<Failure, MoviePagedResponse> =
        Either.Left(failure)
}