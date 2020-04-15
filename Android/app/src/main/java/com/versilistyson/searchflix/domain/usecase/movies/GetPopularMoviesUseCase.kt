package com.versilistyson.searchflix.domain.usecase.movies

import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.entities.Movie
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.domain.usecase.UseCase
import javax.inject.Inject

class GetPopularMoviesUseCase
@Inject constructor(private val movieRepository: MovieRepository): UseCase<List<Movie>,GetPopularMoviesUseCase.Params>() {

    data class Params(val language: String = "en-US", val page: Int = 1)

    override suspend fun run(params: Params): Either<Failure, List<Movie>> =
        movieRepository.getPopularMovies(params.language, params.page)
}