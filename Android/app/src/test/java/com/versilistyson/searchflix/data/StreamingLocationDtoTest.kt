package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.remote.dto.StreamingLocationDto
import com.versilistyson.searchflix.domain.entities.StreamingLocation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StreamingLocationDtoTest {
    @Test
    fun `StreamingLocationDto should properly map to corresponding entity`() {
        // GIVEN
        val actual: StreamingLocation
        val expected = StreamingLocation(
            iconPath = "iconPath",
            displayName = "displayName",
            pathToMedia = "pathToMedia"
        )
        val testStreamingLocationDto = StreamingLocationDto(
            iconPath = "iconPath",
            displayName = "displayName",
            pathToMedia = "pathToMedia"
        )
        // WHEN
        actual = testStreamingLocationDto.mapToEntity()
        // THEN
        assertEquals(expected, actual)
    }
}