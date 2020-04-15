package com.versilistyson.searchflix.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.versilistyson.searchflix.data.datasource.search.MovieRemoteSource
import com.versilistyson.searchflix.data.network.dto.Dto
import com.versilistyson.searchflix.data.network.dto.MovieDto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository
@Inject constructor(val movieRemoteSource: MovieRemoteSource) {
    suspend fun searchMovies(
        query: String,
        page: Int = 1,
        language: String = "en-US",
        isAdultIncluded: Boolean = false
    ) : Either<Failure, List<Movie>> {
        val response = movieRemoteSource.searchMovies(query, page, language, isAdultIncluded)
        return response.foldAndGet(::handleFailure,::handleNetworkResult)
    }

    suspend fun getPopularMovies(language: String, page: Int): Either<Failure, List<Movie>> {
        val response = movieRemoteSource.fetchPopularMovies(language,page)
        return response.foldAndGet(::handleFailure, ::handleNetworkResult)
    }

    private fun handleNetworkResult(networkResult: NetworkResult<List<MovieDto>>) : Either<Failure, List<Movie>> =
        when(networkResult) {
            is NetworkResult.Empty -> Either.Right(emptyList())
            is NetworkResult.Data -> {
                val movies = mutableListOf<Movie>()
                networkResult.value.forEach {
                    movies.add(it.mapToEntity())
                }
                Either.Right(movies)
            }
        }

    // TODO: Maybe add some feature failures
    private fun handleFailure(failure: Failure) : Either<Failure, List<Movie>> =
        Either.Left(failure)
}