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
        page: LiveData<Int>,
        language: String = "en-US",
        isAdultIncluded: Boolean = false
    ): Flow<Either<Failure, List<Movie>>> =
        flow {
            page.asFlow().collect { pageNum ->
                when(val networkResponse = movieRemoteSource.searchMovies(query,pageNum,language,isAdultIncluded)) {
                    is Either.Left -> emit(Either.Left(networkResponse.left))
                    is Either.Right -> {
                        when(val networkResult = networkResponse.right) {
                            is NetworkResult.Empty -> Either.Right(emptyList<Movie>())
                            is NetworkResult.Data -> {
                                val movies = mutableListOf<Movie>()
                                networkResult.value.forEach { movies.add(it.mapToEntity()) }
                                Either.Right(movies)
                            }
                        }
                    }
                }
            }
        }


}