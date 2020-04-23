package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.domain.entities.MediaSingleResponse

data class MovieSingleResponseDto(
    @Json(name = "results")
    val movieResults: List<MovieDto>
) : Dto<MediaSingleResponse.MovieSingleResponse>() {

    override val entityMapper: Mappable<MediaSingleResponse.MovieSingleResponse>
        get() = object : Mappable<MediaSingleResponse.MovieSingleResponse> {
            override fun map(): MediaSingleResponse.MovieSingleResponse =
                MediaSingleResponse.MovieSingleResponse(
                    movieResults.map { movieDto ->
                        movieDto.mapToEntity()
                    }
                )

        }

}