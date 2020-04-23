package com.versilistyson.searchflix.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface StreamLookupApi {

    @GET("idlookup?source=tmdb")
    fun fetchStreamingPlatformsById(
        @Query("source_id")
        source_id: Int,
        @Query("country")
        country: String
    )
}