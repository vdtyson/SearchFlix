package com.versilistyson.searchflix.domain.usecase.movies

import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.entities.Movie
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.domain.usecase.UseCase
import javax.inject.Inject

class SearchMoviesUseCase
@Inject constructor(private val movieRepository: MovieRepository) : UseCase<List<Movie>, SearchMoviesUseCase.Params>() {
    data class Params(
        val query: String,
        val page: Int,
        val language: String = "en-US",
        val isAdultIncluded: Boolean = false
    )

    override suspend fun run(params: Params): Either<Failure, List<Movie>> =
        movieRepository.searchMovies(params.query,params.page,params.language,params.isAdultIncluded)
}