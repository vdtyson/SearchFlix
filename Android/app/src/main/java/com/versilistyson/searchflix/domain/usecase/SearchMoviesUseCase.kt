package com.versilistyson.searchflix.domain.usecase

import androidx.lifecycle.LiveData
import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.entities.Movie
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase
@Inject constructor(private val movieRepository: MovieRepository) :
    FlowUseCase<List<Movie>, SearchMoviesUseCase.Params>() {
    data class Params(
        val query: String,
        val page: LiveData<Int>,
        val language: String = "en-US",
        val isAdultIncluded: Boolean = false
    )

    override suspend fun run(params: Params): Flow<Either<Failure, List<Movie>>> =
        movieRepository.searchMovies(params.query,params.page,params.language,params.isAdultIncluded)
}