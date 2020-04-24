package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.entities.StreamLocationsResult
import com.versilistyson.searchflix.domain.entities.StreamingLocation

data class StreamLocationsResultDto(
    @Json(name = "locations")
    val streamingLocationDtoList: List<StreamingLocationDto>
) : Dto<StreamLocationsResult>() {

    override fun toEntity(): StreamLocationsResult {

        return StreamLocationsResult(streamLocations = streamingLocationDtoList.map {streamingLocationDto ->
            streamingLocationDto.mapToEntity()
        })
    }

}