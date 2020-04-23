package com.versilistyson.searchflix.data.remote.dto

import com.squareup.moshi.Json
import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.domain.entities.StreamingLocation

data class StreamingLocationDto (
    @Json(name = "icon")
    val iconPath: String,
    @Json(name = "display_name")
    val displayName: String,
    @Json(name = "url")
    val pathToMedia: String
) : Dto<StreamingLocation>() {

    override fun toEntity(): StreamingLocation =
        StreamingLocation(
            iconPath = iconPath,
            displayName = displayName,
            pathToMedia = pathToMedia
        )
}