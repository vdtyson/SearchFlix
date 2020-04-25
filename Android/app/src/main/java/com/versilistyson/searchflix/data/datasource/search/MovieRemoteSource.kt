package com.versilistyson.searchflix.data.datasource.search

import com.versilistyson.searchflix.data.remote.api.MovieApi
import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.data.remote.dto.MoviePagedResponseDto
import com.versilistyson.searchflix.data.remote.dto.MovieSingleResponseDto
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.data.util.getResult
import javax.inject.Inject

class MovieRemoteSource
@Inject constructor(private val movieApi: MovieApi) {

    suspend fun fetchPopularMovies(language: String, page: Int): Either<Failure, NetworkResult<MoviePagedResponseDto>> {
        val response = movieApi.fetchPopularMovies(language,page)
        return response.getResult()
    }

    suspend fun fetchTopRatedMovies(language: String, page: Int): Either<Failure, NetworkResult<MoviePagedResponseDto>> {
        val response = movieApi.fetchTopRatedMovies(language,page)
        return response.getResult()
    }

    suspend fun fetchUpcomingMovies(language: String): Either<Failure, NetworkResult<MovieSingleResponseDto>> {
        val response = movieApi.fetchUpcomingMovies(language)
        return response.getResult()
    }

    fun fetchMovieQueryResults(query: String, isAdultIncluded: Boolean, language: String, page: Int) =
        movieApi.fetchMovieQueryResults(query, isAdultIncluded, language,page)



}