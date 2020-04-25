package com.versilistyson.searchflix.data.repository

import com.versilistyson.searchflix.data.datasource.search.MovieQueryPagedDataSourceFactory
import com.versilistyson.searchflix.data.datasource.search.MovieRemoteSource
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.data.util.handleFailure
import com.versilistyson.searchflix.data.util.handleNetworkResult
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
        isAdultIncluded: Boolean,
        language: String
    ) {
        val factory = MovieQueryPagedDataSourceFactory(query, isAdultIncluded, language) { page ->
            movieRemoteSource.fetchMovieQueryResults(query, isAdultIncluded, language, page)
        }
    }

}