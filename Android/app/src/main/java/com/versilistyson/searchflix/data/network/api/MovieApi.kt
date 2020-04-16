package com.versilistyson.searchflix.data.network.api

import com.squareup.moshi.Json
import com.versilistyson.searchflix.data.network.dto.MovieDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/search/movie")
    suspend fun fetchMoviesFromQuery(
        @Body
        @Json(name = "query") query: String,
        @Json(name = "adult") isAdultIncluded: Boolean,
        @Json(name = "page") page: Int,
        @Json(name = "language") language: String
    ) : Response<List<MovieDto>>

    @GET("/movie/popular")
    suspend fun fetchPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<List<MovieDto>>
}