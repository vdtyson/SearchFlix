package com.versilistyson.searchflix.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.versilistyson.searchflix.data.datasource.movie.MovieLocalSource
import com.versilistyson.searchflix.data.datasource.movie.MovieRemoteSource
import com.versilistyson.searchflix.data.local.model.MediaData
import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.data.remote.dto.MoviePagedResponseDto
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.entities.MediaPagedResponse
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.jupiter.api.Assertions

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    companion object {
        private const val TEST_LANGUAGE = ""
        private const val TEST_PAGE = 1
        private val testMoviePagedResponseDto =
            MoviePagedResponseDto(
                1,
                2,
                3,
                movieDtoResults = listOf(
                    MovieDto(
                        id = 1,
                        title = "",
                        releaseDate = "",
                        overview = "",
                        posterPath = "",
                        backdropPath = "",
                        popularity = 0.0,
                        voteAverage = 0.0,
                        voteCount = 100,
                        hasVideo = false
                    )
                )
            )

        private val testMovieFavoritedMediaData = MediaData(
            mediaId = 0L,
            title = "",
            overview = "",
            releaseDate = "",
            posterPath = "",
            backdropPath = "",
            voteAverage = 1.0,
            voteCount = 300,
            isFavorite = true,
            type = "MOVIE"
        )

        private val testMediaDataList = listOf(
            testMovieFavoritedMediaData,
            testMovieFavoritedMediaData,
            testMovieFavoritedMediaData
        )
    }

    private val movieLocalSourceMock: MovieLocalSource = mock()
    private val movieRemoteSourceMock: MovieRemoteSource = mock()
    private val movieRepo = MovieRepository(movieLocalSourceMock, movieRemoteSourceMock)

    @Test
    fun `getPopularMovies() should properly return failure`() {

        runBlockingTest {
            // GIVEN
            val expected = Either.Left(Failure.ServerError())

            // WHEN
            whenever(movieRemoteSourceMock.fetchPopularMovies(TEST_LANGUAGE, TEST_PAGE))
                .thenReturn(Either.Left(Failure.ServerError()))

            val actual: Either<Failure, MediaPagedResponse.MoviePagedResponse> =
                movieRepo.getPopularMovies(TEST_LANGUAGE, TEST_PAGE)
            // THEN
            Assertions.assertEquals(expected, actual)
        }

    }

    @Test
    fun `getPopularMovies() should properly handle empty NetworkResult`() {

        runBlockingTest {
            // GIVEN
            val expected = Either.Right(MediaPagedResponse.MoviePagedResponse())

            //WHEN
            whenever(movieRemoteSourceMock.fetchPopularMovies(TEST_LANGUAGE, TEST_PAGE))
                .thenReturn(Either.Right(NetworkResult.Empty()))

            val actual =
                movieRepo.getPopularMovies(TEST_LANGUAGE, TEST_PAGE)

            // THEN
            Assertions.assertEquals(expected, actual)
        }
    }

    @Test
    fun `getPopularMovies() should properly handle NetworkResult with data and list of movies`() {

        runBlockingTest {
            // GIVEN
            val expected =
                Either.Right(testMoviePagedResponseDto.mapToEntity())

            whenever(movieRemoteSourceMock.fetchPopularMovies(TEST_LANGUAGE, TEST_PAGE))
                .thenReturn(Either.Right(NetworkResult.Data(testMoviePagedResponseDto)))
            // WHEN

            val actual =
                movieRepo.getPopularMovies(TEST_LANGUAGE, TEST_PAGE)

            // THEN
            Assertions.assertEquals(expected, actual)
        }
    }

    @Test
    fun `getFavoriteMoviesFlow() should properly stream movie list`() {

        runBlockingTest {
            // GIVEN
            val expected = testMediaDataList.map {mediaData ->
                mediaData.mapToEntity()
            }

            whenever(movieLocalSourceMock.getMovieFavoritesFlow())
                .thenReturn(flowOf(testMediaDataList))

            // WHEN
            val favoritesFlow: Flow<List<Media>> = movieRepo.getFavoriteMoviesFlow()

            // THEN
            favoritesFlow.collect { actual ->
                Assertions.assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `getFlowOfIsFavorite() should return true`() {

        runBlockingTest() {
            // GIVEN
            val expected = true

            whenever(movieLocalSourceMock.getIsFavoriteByMediaId(1))
                .thenReturn(flowOf(true))

            // WHEN
            val isFavoriteFlow = movieRepo.getFlowOfIsFavorite(1)

            // THEN
            isFavoriteFlow.collect {actual ->
                Assertions.assertEquals(expected, actual)
            }
        }
    }
}