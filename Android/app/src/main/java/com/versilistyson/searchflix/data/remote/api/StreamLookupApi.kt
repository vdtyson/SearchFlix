package com.versilistyson.searchflix.data.remote.api

import com.versilistyson.searchflix.data.remote.dto.StreamLookupResponseDto
import com.versilistyson.searchflix.domain.entities.StreamLookupResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface StreamLookupApi {

    @GET("idlookup?source=tmdb")
    suspend fun fetchAvailableStreamingPlatforms(
        @Query("source_id")
        mediaId: Int,
        @Query("country")
        country: String
    ) : Response<StreamLookupResponseDto>
}