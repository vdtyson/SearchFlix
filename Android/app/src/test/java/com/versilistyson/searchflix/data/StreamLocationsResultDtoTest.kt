package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.remote.dto.StreamLocationsResultDto
import com.versilistyson.searchflix.domain.entities.StreamLocationsResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StreamLocationsResultDtoTest {
    @Test
    fun `StreamLocationsResultDto should properly map to corresponding entity`() {
        // GIVEN
        val actual: StreamLocationsResult
        val expected = StreamLocationsResult()
        val testStreamLocationsResultDto = StreamLocationsResultDto(emptyList())
        // WHEN
        actual = testStreamLocationsResultDto.mapToEntity()
        // THEN
        assertEquals(expected, actual)
    }
}