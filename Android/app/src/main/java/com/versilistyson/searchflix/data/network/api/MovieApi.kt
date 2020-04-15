package com.versilistyson.searchflix.data.network.api

import com.squareup.moshi.Json
import com.versilistyson.searchflix.data.network.dto.MovieDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface MovieApi {
    @GET("/search/movie")
    suspend fun queryMovies(
        @Body
        @Json(name = "query") query: String,
        @Json(name = "adult") isAdultIncluded: Boolean,
        @Json(name = "page") page: Int,
        @Json(name = "language") language: String
    ) : Response<List<MovieDto>>
}