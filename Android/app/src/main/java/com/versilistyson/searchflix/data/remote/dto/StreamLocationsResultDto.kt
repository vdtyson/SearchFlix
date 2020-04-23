package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.entities.StreamLocationsResult

data class StreamLocationsResultDto(
    @Json(name = "Locations")
    val streamingLocations: List<StreamingLocationDto>
) : Dto<StreamLocationsResult>() {

    override fun toEntity(): StreamLocationsResult =
        StreamLocationsResult(
            streamLocations = streamingLocations.map { streamingLocationDto ->
                streamingLocationDto.mapToEntity()
            }
        )
}