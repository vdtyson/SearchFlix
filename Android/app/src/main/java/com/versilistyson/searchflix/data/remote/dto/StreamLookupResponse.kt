package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.entities.StreamLookupResponse

data class StreamLookupResponseDto(
    @Json(name = "collection")
    val streamLocationsResultDto: StreamLocationsResultDto
) : Dto<StreamLookupResponse>() {

    override fun toEntity(): StreamLookupResponse =
        StreamLookupResponse(streamLocationsResult = streamLocationsResultDto.mapToEntity())
}