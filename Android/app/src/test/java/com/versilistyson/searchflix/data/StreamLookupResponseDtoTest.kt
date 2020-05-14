package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.remote.dto.StreamLocationsResultDto
import com.versilistyson.searchflix.data.remote.dto.StreamLookupResponseDto
import com.versilistyson.searchflix.domain.entities.StreamLocationsResult
import com.versilistyson.searchflix.domain.entities.StreamLookupResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StreamLookupResponseDtoTest {
    @Test
    fun `StreamLookupResponseDto should properly map to corresponding entity`() {
        // GIVEN
        val actual: StreamLookupResponse
        val expected = StreamLookupResponse(StreamLocationsResult())
        val testStreamLookupResponseDto = StreamLookupResponseDto(StreamLocationsResultDto(emptyList()))
        // WHEN
        actual = testStreamLookupResponseDto.mapToEntity()
        // THEN
        assertEquals(expected, actual)
    }
}