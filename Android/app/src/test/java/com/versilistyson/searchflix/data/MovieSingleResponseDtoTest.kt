package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.remote.dto.MovieSingleResponseDto
import com.versilistyson.searchflix.domain.entities.MediaSingleResponse.MovieSingleResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MovieSingleResponseDtoTest {
    @Test
    fun `MovieSingleResponseDto should properly map to corresponding entity`() {
        // GIVEN
        val actual: MovieSingleResponse
        val expected = MovieSingleResponse(emptyList())
        val testMediaSingleResponseDto = MovieSingleResponseDto(emptyList())
        // WHEN
        actual = testMediaSingleResponseDto.mapToEntity()
        // THEN
        assertEquals(expected, actual)
    }
}