package com.versilistyson.searchflix.data.datasource.search

import com.versilistyson.searchflix.data.network.api.MovieApi
import com.versilistyson.searchflix.data.network.dto.MovieDto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.data.util.getResult
import javax.inject.Inject

class MovieRemoteSource
@Inject constructor(private val movieApi: MovieApi) {
    suspend fun searchMovies(
        query: String,
        page: Int,
        language: String,
        isAdultIncluded: Boolean
    ): Either<Failure.ServerError, NetworkResult<List<MovieDto>>> {
        val response = movieApi.fetchMoviesFromQuery(query, isAdultIncluded, page, language)
        return response.getResult()
    }

    suspend fun fetchPopularMovies(language: String, page: Int): Either<Failure.ServerError, NetworkResult<List<MovieDto>>> {
        val response = movieApi.fetchPopularMovies(language,page)
        return response.getResult()
    }
}