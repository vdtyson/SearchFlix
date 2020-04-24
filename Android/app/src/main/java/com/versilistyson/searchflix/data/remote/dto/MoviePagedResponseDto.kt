package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.domain.entities.MediaPagedResponse.*

data class MoviePagedResponseDto(
    @Json(name = "page")
    val currentPage: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")//Json(name = "results")
    val movieDtoResults: List<MovieDto>
) : Dto<MoviePagedResponse>() {

    override fun toEntity(): MoviePagedResponse =
        MoviePagedResponse(
            currentPage = currentPage,
            totalResults = totalResults,
            totalPages = totalPages,
            movieResults = movieDtoResults.map { movieDto ->
                movieDto.mapToEntity()
            }
        )

}
