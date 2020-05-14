package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.domain.entities.Media
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MovieDtoTest {
    @Test
    fun `MovieDto should correctly map to Movie Entity`() {
        // GIVEN
        val testMovieDto = MovieDto(
            id = 1,
            title = "sumn",
            releaseDate = "some date",
            overview = "an overview",
            posterPath = null,
            backdropPath = "backdrop",
            popularity = 1.2,
            voteAverage = 1.1,
            voteCount = 8,
            hasVideo = true
        )

        val expected = Media.Movie(
            movieId = 1,
            title = "sumn",
            movieReleaseDate = "some date",
            overview = "an overview",
            moviePosterPath = "",
            movieBackdropPath = "backdrop",
            popularity = 1.2,
            movieVoteAverage = 1.1,
            movieVoteCount = 8,
            isFavorite = false
        )

        val actual: Media.Movie
        // WHEN
        actual = testMovieDto.mapToEntity()
        // THEN
       assertEquals(expected, actual)
    }
}