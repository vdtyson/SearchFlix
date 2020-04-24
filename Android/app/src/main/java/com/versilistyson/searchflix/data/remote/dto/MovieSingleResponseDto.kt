package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.domain.entities.MediaSingleResponse

data class MovieSingleResponseDto(
    @Json(name = "results")
    val movieResults: List<MovieDto>
) : Dto<MediaSingleResponse.MovieSingleResponse>() {

    override fun toEntity(): MediaSingleResponse.MovieSingleResponse =
        MediaSingleResponse.MovieSingleResponse(
            movieResults.map { movieDto ->
                movieDto.mapToEntity()
            }
        )


}