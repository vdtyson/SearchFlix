package com.versilistyson.searchflix.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.versilistyson.searchflix.data.datasource.movie.MovieLocalSource
import com.versilistyson.searchflix.data.datasource.movie.MovieQueryPagedDataSourceFactory
import com.versilistyson.searchflix.data.datasource.movie.MovieRemoteSource
import com.versilistyson.searchflix.data.util.handleFailure
import com.versilistyson.searchflix.data.util.handleNetworkResult
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.entities.MediaPagedResponse.MoviePagedResponse
import com.versilistyson.searchflix.domain.entities.MediaSingleResponse.MovieSingleResponse
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class MovieRepository
@Inject constructor(
    private val movieLocalSource: MovieLocalSource,
    private val movieRemoteSource: MovieRemoteSource
) {

    suspend fun save(vararg movies: Media.Movie) {

        val mappedMovies =
            movies.map { movie -> movie.mapToPersistent() }.toTypedArray()

        movieLocalSource.insert(*mappedMovies)
    }

    @ExperimentalCoroutinesApi
    fun getFavoriteMoviesFlow(coroutineContext: CoroutineContext = Dispatchers.IO) =
        movieLocalSource.getMovieFavoritesFlow().map { list ->
            list.map {mediaData ->
                mediaData.mapToEntity()
            }
        }.flowOn(coroutineContext)

    @ExperimentalCoroutinesApi
    fun getFlowOfIsFavorite(id: Int, coroutineContext: CoroutineContext = Dispatchers.IO) =
        movieLocalSource.getIsFavoriteByMediaId(id.toLong()).flowOn(coroutineContext)

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

    fun queryMovies(
        query: String,
        isAdultIncluded: Boolean,
        language: String,
        pageSize: Int
    ): LiveData<PagedList<Media.Movie>> {
        val factory =
            MovieQueryPagedDataSourceFactory(
                query,
                isAdultIncluded,
                language
            ) { page ->
                movieRemoteSource.fetchMovieQueryResults(query, isAdultIncluded, language, page)
            }.mapByPage { movieDtoList ->
                movieDtoList.map { it.mapToEntity() }
            }

        val livePageBuilder = LivePagedListBuilder(factory, pageSize)

        return livePageBuilder.build()
    }



}

