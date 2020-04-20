package com.versilistyson.searchflix.data.repository

import com.versilistyson.searchflix.data.datasource.PagedFeedDataSourceFactory
import com.versilistyson.searchflix.data.datasource.search.MovieRemoteSource
import com.versilistyson.searchflix.data.network.dto.MovieDto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.entities.Media
import javax.inject.Inject

class MovieRepository
@Inject constructor(private val movieRemoteSource: MovieRemoteSource) {

    private suspend fun fetchPopularMovies(
        language: String,
        page: Int
    ): Either<Failure, List<Media.Movie>> {
        val response = movieRemoteSource.fetchPopularMovies(language, page)
        return response.foldAndGet(::handleFailure, ::handleNetworkResult)
    }

    suspend fun getPopularMoviesFeed(language: String): PagedFeedDataSourceFactory<Media.Movie> =
        PagedFeedDataSourceFactory { page -> fetchPopularMovies(language, page) }

    private suspend fun queryMovies(
        query: String,
        page: Int,
        language: String,
        isAdultIncluded: Boolean
    ): Either<Failure, List<Media.Movie>> {
        val response = movieRemoteSource.searchMovies(query, page, language, isAdultIncluded)
        return response.foldAndGet(::handleFailure, ::handleNetworkResult)
    }

    suspend fun getQueriedMoviesFeed(
        query: String,
        language: String,
        isAdultIncluded: Boolean
    ) = PagedFeedDataSourceFactory { page -> queryMovies(query, page, language, isAdultIncluded) }


    private fun handleNetworkResult(networkResult: NetworkResult<List<MovieDto>>): Either<Failure, List<Media.Movie>> =
        when (networkResult) {
            is NetworkResult.Empty -> Either.Right(emptyList())
            is NetworkResult.Data -> {
                val movies = mutableListOf<Media.Movie>()
                networkResult.value.forEach {
                    movies.add(it.mapToEntity())
                }
                Either.Right(movies)
            }
        }

    // TODO: Maybe add some feature failures
    private fun handleFailure(failure: Failure): Either<Failure, List<Media.Movie>> =
        Either.Left(failure)
}