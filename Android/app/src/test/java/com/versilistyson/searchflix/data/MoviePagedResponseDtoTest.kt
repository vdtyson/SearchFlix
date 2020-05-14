package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.data.remote.dto.MoviePagedResponseDto
import com.versilistyson.searchflix.domain.entities.MediaPagedResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MoviePagedResponseDtoTest {
    @Test
    fun `should properly map to MoviePagedResponse entity`() {
        // GIVEN
        val testMoviePagedResponseDto = MoviePagedResponseDto(
            currentPage = 1,
            totalResults = 3,
            totalPages = 5,
            movieDtoResults = emptyList()
        )
        val expected = MediaPagedResponse.MoviePagedResponse(
            currentPage = 1,
            totalResults = 3,
            totalPages = 5,
            movieResults = emptyList()
        )
        val actual: MediaPagedResponse.MoviePagedResponse
        // WHEN
        actual = testMoviePagedResponseDto.mapToEntity()
        // THEN
        assertEquals(expected, actual)
    }
}